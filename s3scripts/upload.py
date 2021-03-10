import boto3
import botocore
import os
import sys

"""
Note: The current capability of this script is to upload either a file or a
folder to an S3 bucket, given the file or folder's local path.

If a folder is to be uploaded, its path must end with a '/', otherwise it will be treated
as a file.
"""

BUCKET_NAME = '' # replace with your bucket name
ABS_PATH = '' # local path of folder or file to upload

"""
params:
- path: local path of file or directory to be uploaded.
"""
def upload(path):
    s3  = boto3.resource('s3')
    bucket = s3.Bucket(BUCKET_NAME)

    # if path belongs to a folder
    if (path[-1] == '/'):
        # use os.walk to traverse directory tree
        for subdir, dirs, files in os.walk(path):
            for file in files:
                full_path = os.path.join(subdir, file)
                with open(full_path, 'rb') as data:
                    print(full_path)
                    bucket.put_object(Key=full_path[len(path):], Body=data)
    else: # path belongs to a file. Do not traverse directory tree.
        try:
            file_name = os.path.basename(os.path.normpath(path))
            s3.Bucket(BUCKET_NAME).upload_file(path, file_name)
            print("The file '%s' was successfully uploaded and saved as '%s'!" % (path, file_name))
        except botocore.exceptions.ClientError as e:
            if e.response['Error']['Code'] == "404":
                print("The object does not exist.")
            else:
                raise

if __name__ == "__main__":
    try:
        upload(ABS_PATH)
        print("The contents of the '%s' object has successfully uploaded" % ABS_PATH)
    except botocore.exceptions.ClientError as e:
       if e.response['Error']['Code'] == "404":
           print("The object does not exist.")
       else:
           raise
