from testcontainers.mysql import MySqlContainer
from testcontainers.core.container import DockerContainer
from testcontainers.core.network import Network
from testcontainers.core.waiting_utils import wait_for_logs

from testcontainers.core.image import DockerImage
import socket
from contextlib import closing
import requests
import time
import docker
import os
import re


### Environment Variables




# def find_free_port():
#     with closing(socket.socket(socket.AF_INET, socket.SOCK_STREAM)) as s:
#         s.bind(('', 0))
#         s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
#         return s.getsockname()[1]


# # my_sql_port = find_free_port()
client = docker.from_env()

try:
    femr_image = os.getenv("FEMR_IMAGE_NAME", "femr-femr")
    
    # Verify Image exists
    client.images.get(femr_image)
except:
    femr_image = None

assert femr_image is not None, "FEMR image not found, build image to 'femr-femr' or set FEMR_IMAGE_NAME environment variable to the correct image name"

sql_container_spec = MySqlContainer('mysql:9.1.0', "femr", "password", "password", "femr_db", 3306)\
    .with_network_aliases("mysql")\
    .with_command("mysqld --log-bin-trust-function-creators=1")


import pytest

@pytest.fixture(scope='function', autouse=True)
def run_before_and_after_tests(request):
    """Fixture to execute asserts before and after a test is run"""

    network_name = "femr_test_network" + str(time.time())
    network = client.networks.create(network_name, driver="bridge")

    def cleanup():
        network.remove()
    
    request.addfinalizer(cleanup)

    with sql_container_spec as mysql:
        network.connect(mysql._container.id, aliases=["db"])

        femr_container_spec = DockerContainer(femr_image, network=network_name)\
            .with_bind_ports("9000", "9000")\
            .with_env("DB_URL", f'jdbc:mysql://db:3306/femr_db?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true')\
            .with_env("DB_USER", 'femr')\
            .with_env("DB_PASS", 'password')\
            .with_env("IS_DOCKER",'true')\
                

        with femr_container_spec as femr_container:
            wait_for_logs(femr_container, re.compile(".*Listening for HTTP on.*", flags=re.DOTALL | re.MULTILINE).search)

            print("Femr Started")

            femr_address = f"http://{femr_container.get_container_host_ip()}:{femr_container.get_exposed_port(9000)}"
            os.environ["FEMR_ADDRESS"] = femr_address
            
            yield
    
