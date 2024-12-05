from testcontainers.mysql import MySqlContainer
from testcontainers.core.container import DockerContainer
from testcontainers.core.network import Network
import sqlalchemy
from testcontainers.core.waiting_utils import wait_for_logs

from testcontainers.core.image import DockerImage
import socket
from contextlib import closing
import requests
import time
import docker
import re

def find_free_port():
    with closing(socket.socket(socket.AF_INET, socket.SOCK_STREAM)) as s:
        s.bind(('', 0))
        s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        return s.getsockname()[1]


print("Assuming femr docker container is built with tag femr_femr\n\n")

# my_sql_port = find_free_port()
client = docker.from_env()

network_name = "femr_test_network" + str(time.time())
network = client.networks.create(network_name, driver="bridge")

sql_container_spec = MySqlContainer('mysql:9.1.0', "femr", "password", "password", "femr_db", 3306)\
    .with_network_aliases("mysql")\
    .with_command("mysqld --log-bin-trust-function-creators=1")

try:
    with sql_container_spec as mysql:
        network.connect(mysql._container.id, aliases=["db"])

        femr_container_spec = DockerContainer("femr_femr", network=network_name)\
            .with_bind_ports("9000", "9000")\
            .with_env("DB_URL", f'jdbc:mysql://db:3306/femr_db?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true')\
            .with_env("DB_USER", 'femr')\
            .with_env("DB_PASS", 'password')\
            .with_env("IS_DOCKER",'true')\
                

        with femr_container_spec as femr_container:
            wait_for_logs(femr_container, re.compile(".*Listening for HTTP on.*", flags=re.DOTALL | re.MULTILINE).search)
            print("FEMR container is up and running!!!!!!!!!!!!!!!!")
            print(requests.get(f"http://{femr_container.get_container_host_ip()}:{femr_container.get_exposed_port(9000)}"))
finally:
    network.remove()