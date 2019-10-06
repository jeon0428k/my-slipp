from datetime import datetime
import os, subprocess
from contextlib import contextmanager
import xmltodict, json
from pprint import pprint
from xml.dom import minidom

print(0o12)
print(1.2E-2)

print("test : \0a")
print("test : \00a")
print("test : \000a")

a = "012345678"
print(a[1:4])

with open("config.xml") as fd:
    doc = xmltodict.parse(fd.read())
    pprint(json.dumps(doc))

""" 1231
   21313
"""
with open("config.json", "r", encoding='UTF8') as data_file:
    json_data = json.load(data_file)
    pprint(json_data)
    print(json_data["git"])

@contextmanager
def cd(newdir):
    prevdir = os.getcwd()
    os.chdir(os.path.expanduser(newdir))
    try:
        yield
    finally:
        os.chdir(prevdir)

# "C:\Program Files (x86)\nexacro\17\nexacrodeploy17.exe" -P "C:\G-MES4.0\nexacro\PROJECT\ME.xprj" -O "C:\G-MES4.0\nexacro_generate" -B "C:\G-MES4.0\nexacro\nexacro17lib" -D "C:\G-MES4.0\nexacro_deploy" -COMPRESS -SHRINK -FILE "C:\G-MES4.0\nexacro\PROJECT\Base\test.xfdl"

git_files_path = "resource/kkk.xfdl resource/kkk.xfdl.js"
git_files_comt = """
[11] test
- a
- b
"""
git_branch_new = "JEON_" + datetime.today().strftime("%Y%m%d%H%M%S")
git_branch_mst = "master"

with cd("C:\G-MES4.0\my-slipp"): # os.chdir("C:\G-MES4.0\my-slipp")
    # subprocess.call('git pull')
    # subprocess.call('git add {git_files_path}'.format(git_files_path=git_files_path))
    # subprocess.call('git commit -m "{git_files_comt}"'.format(git_files_comt=git_files_comt))
    # subprocess.call('git push origin {git_branch_mst}'.format(git_branch_mst=git_branch_mst))

    subprocess.call('git pull')
    subprocess.call('git branch {git_branch_new}'.format(git_branch_new=git_branch_new))
    subprocess.call('git checkout {git_branch_new}'.format(git_branch_new=git_branch_new))
    subprocess.call('git add {git_files_path}'.format(git_files_path=git_files_path))
    subprocess.call('git commit -m "{git_files_comt}"'.format(git_files_comt=git_files_comt))
    subprocess.call('git push origin {git_branch_new}'.format(git_branch_new=git_branch_new))
    subprocess.call('git checkout {git_branch_mst}'.format(git_branch_mst=git_branch_mst))
    subprocess.call('git merge {git_branch_new}'.format(git_branch_new=git_branch_new))
    subprocess.call('git push origin {git_branch_mst}'.format(git_branch_mst=git_branch_mst))
    subprocess.call('git branch -d {git_branch_new}'.format(git_branch_new=git_branch_new))