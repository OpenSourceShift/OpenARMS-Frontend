#!/usr/bin/env python
import os.path, shutil, random, fileinput, re, sys, getpass, md5, base64
from Crypto.Cipher import AES

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

def secretKey():
	return ''.join([random.choice('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789') for i in range(64)])

def getOrCopyConfigurationFile(app):
	scriptDirectory = os.path.dirname(__file__)
	sampleConfigurationFilename = os.path.join(scriptDirectory, '..', app, 'conf', 'application.sample.conf')
	currentConfigurationFilename = os.path.join(scriptDirectory, '..', app, 'conf', 'application.conf')
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
	frontendConfigurationFile = getOrCopyConfigurationFile('Frontend')
	serviceConfigurationFile = getOrCopyConfigurationFile('Service')

	print 'You will have to select the username and password for the administrative user, to gain access to the administrative CRUD interface.';
	print 'Please notice that this will be stored encrypted and hashed in the configuration file of the Service.'
	adminUser = raw_input('Administrative username (leave blank for admin): ');
	if not adminUser:
		adminUser = 'admin'

	adminPassword = None
	adminPassword = getpass.getpass('Administrative password: ');

	if len(adminPassword) < 6:
		print "It is strongly disencouraged to use passwords of length lesser than 8 characters."
	print "Using username: %s and password: %s" % (adminUser, '*' * len(adminPassword))

	frontendSecretKey = secretKey()
	replaceAll(frontendConfigurationFile, r'application.secret=.*', 'application.secret=%s' % frontendSecretKey, True)

	serviceSecretKey = secretKey()
	replaceAll(serviceConfigurationFile, r'application.secret=.*', 'application.secret=%s' % serviceSecretKey, True)

	pad = lambda s: s + (BLOCK_SIZE - len(s) % BLOCK_SIZE) * PADDING
	# create a cipher object using the random secret
	#print "AES Encrypting using: %s" % serviceSecretKey[0:16]
	cipher = AES.new(serviceSecretKey[0:16])
	paddedAdminPassword = pad(adminPassword)
	encryptedAdminPassword = cipher.encrypt(paddedAdminPassword).encode('hex')
	hashedEncryptedAdminPassword = base64.b64encode(md5.new(encryptedAdminPassword).digest())

	#print "paddedAdminPassword: %s" % paddedAdminPassword
	#print "encryptedAdminPassword: %s" % encryptedAdminPassword
	#print "hashedEncryptedAdminPassword: %s" % hashedEncryptedAdminPassword
	replaceAll(serviceConfigurationFile, r'crud.admin_username=.*', 'crud.admin_username=%s' % adminUser, True)
	replaceAll(serviceConfigurationFile, r'crud.admin_password=.*', 'crud.admin_password=%s' % hashedEncryptedAdminPassword, True)
