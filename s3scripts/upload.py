import boto3
import botocore
import os
import sys
import errno

"""
Note: The current capability of this script is to upload either a file or a
folder to an S3 bucket, given the file or folder's local path.

If a folder is to be uploaded, its path must end with a '/', otherwise it will be treated
as a file.
"""

BUCKET_NAME = '' # replace with your bucket name
ABS_PATH = '' # replace with path of file or folder to upload

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
        for subdir, dirs, files in os.walk(path, onerror=walk_error_handler):
            for file in files:
                full_path = os.path.join(subdir, file)
                try:
                    with open(full_path, 'rb') as data:
                        print(full_path)
                        bucket.put_object(Key=full_path[len(path):], Body=data)
                except (OSError, IOError) as e:
                    print("Error: Invalid path. Cannot be uploaded.")
                    raise
    else: # path belongs to a file. Do not traverse directory tree.
        try:
            file_name = os.path.basename(os.path.normpath(path))
            if (os.path.isfile(file_name)):
                s3.Bucket(BUCKET_NAME).upload_file(path, file_name)
                print("The file '%s' was successfully uploaded and saved as '%s'!" % (path, file_name))
            else:
                print("FileNotFoundError: No such file or directory: ", file_name)
                # the below error gets raised quietly, there is no indication when ran.
                # that is why the print statement above is needed.
                raise FileNotFoundError(
                    errno.ENOENT, os.strerror(errno.ENOENT),file_name)
        except botocore.exceptions.ClientError as e:
            if e.response['Error']['Code'] == "404":
                print("The object does not exist.")
            else:
                raise

def walk_error_handler(exception_instance):
    print("Error: Provided Directory cannot be uploaded)
    raise NotADirectoryError("No such directory exists.")

def main():
    try:
        upload(ABS_PATH)
        print("The contents of the '%s' object has successfully uploaded" % ABS_PATH)
    except botocore.exceptions.ClientError as e:
        if e.response['Error']['Code'] == "404":
            print("The object does not exist.")
        else:
            raise

if __name__ == "__main__":
    main()