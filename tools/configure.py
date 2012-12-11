#!/usr/bin/env python
import os.path, shutil, random, fileinput, re, sys, md5, base64
from Crypto.Cipher import AES
from getpass import getpass

BLOCK_SIZE = 16
PADDING = ' '

def printBanner():
	print "  ____                ___   ___  __  _______"
	print " / __ \___  ___ ___  / _ | / _ \/  |/  / __/"
	print "/ /_/ / _ \/ -_) _ \/ __ |/ , _/ /|_/ /\ \  "
	print "\____/ .__/\__/_//_/_/ |_/_/|_/_/  /_/___/  "
	print "    /_/                                     "

def replaceAll(file, searchExp, replaceExp, regexp=False):
	if not regexp:
		replaceExp = replaceExp.replace('\\', '\\\\')
		searchExp = searchExp.replace('$', '\\$')
		searchExp = searchExp.replace('{', '\\{')
		searchExp = searchExp.replace('}', '\\}')
		searchExp = searchExp.replace('.', '\\.')
	for line in fileinput.input(file, inplace=1):
		line = re.sub(searchExp, replaceExp, line)
		sys.stdout.write(line)

def generateSecretKey():
	return ''.join([random.choice('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789') for i in range(64)])

def getOrCopyConfigurationFile():
	scriptDirectory = os.path.dirname(__file__)
	sampleConfigurationFilename = os.path.join(scriptDirectory, '..', 'conf', 'application.sample.conf')
	currentConfigurationFilename = os.path.join(scriptDirectory, '..', 'conf', 'application.conf')
	return getOrCopyFile(sampleConfigurationFilename, currentConfigurationFilename);

def getOrCopyFile(src, dst):
	if os.path.isfile(dst):
		return os.path.abspath(dst)
	elif not os.path.isfile(src):
		raise "Missing a file in the project, expected file at %s" % src
	else:
		shutil.copyfile(src, dst)
		if os.path.isfile(dst):
			# Copy the sample file.
			return os.path.abspath(dst)
		else:
			raise "Couldn't copy the file."

if __name__ == '__main__':
	printBanner()
	configurationFile = getOrCopyConfigurationFile()

	print "Generating a new secret."
	secretKey = generateSecretKey()
	replaceAll(configurationFile, r'application.secret=.*', 'application.secret=%s' % secretKey, True)

