#!/usr/bin/python
import sys
import os.path
import csv

def printUsage():
	print "Usage: %s source-file-or-directory destination-directory [reference-file] [--print-tree]" % sys.argv[0]

def printStringTree(strings, seperator = ".", indent = "\t"):
	flatTree = generateFlatStringTree(strings, seperator, indent)
	for line in flatTree:
		print line

def generateFlatStringTree(strings, seperator = ".", indent = "\t"):
	result = list()
	tree = generateStringTree(strings, seperator)
	sortedKeys = sorted(tree.keys())
	for key in sortedKeys:
		result.append(key)
		subTree = generateFlatStringTree(tree[key], seperator, indent)
		for subKey in subTree:
			result.append(indent + subKey)
	return result

def generateStringTree(strings, seperator = "."):
	result = dict()
	for s in strings:
		split = s.split(seperator, 1)
		if split[0] not in result:
			result[split[0]] = list()
		if len(split) > 1:
			result[split[0]].append(split[1])
	return result

def readLanguageFile(f):
	result = []
	with open(f) as fileOpened:
		for lineNumber, line in enumerate(fileOpened):
			if not line.strip().startswith("#"):
				row = line.split('=', 1)
				if len(row) == 2:
					result.append((row[0], row[1].strip("\n")))
				else:
					print "Error in file: %s line number %d" % (original, lineNumber+1)
	return result

def getLanguageKeys(rows):
	return list(row[0] for row in rows)

def convertFile(original, destination, referenceFile):
	original = os.path.abspath(original)
	destination = os.path.abspath(destination)

	originalFile = readLanguageFile(original)

	print
	print "Processing %s" % original
	with open(destination, "wb") as destinationFile:
		destinationFileWriter = csv.writer(destinationFile)
		for row in originalFile:
			destinationFileWriter.writerow(row)
			if referenceFile and row[0] not in getLanguageKeys(referenceFile):
				print "[needless]\t'%s'" % row[0]
		if referenceFile:
			for key in getLanguageKeys(referenceFile):
				if key not in getLanguageKeys(originalFile):
					print "[missing]\t'%s'" % key

if __name__ == '__main__':
	print "This tool converts messages files to values of comma-seperated-values."

	original = sys.argv[1] if len(sys.argv) >= 2 else None
	destination = sys.argv[2] if len(sys.argv) >= 3 else None
	reference = sys.argv[3] if len(sys.argv) >= 4 else None
	
	if original == None or (not os.path.isdir(original) and not os.path.isfile(original)):
		print "ERROR: You have to set a valid source file or directory."
		printUsage()
		sys.exit(1)
	elif destination == None or not os.path.isdir(destination):
		print "ERROR: You have to set a valid destination directory."
		printUsage()
		sys.exit(2)

	if reference != None and not os.path.isfile(reference):
		print "The referenced messages file is not a file."
	elif reference != None:
		reference = os.path.abspath(reference)
		referenceFile = readLanguageFile(reference)
	else:
		referenceFile = None

	if os.path.isfile(original):
		originalFile = original
		originalFilename = os.path.split(originalFile)
		destinationFile = os.path.join(destination, originalFilename[1] + ".csv")
		convertFile(originalFile, destinationFile, referenceFile)
	elif os.path.isdir(original):
		for f in os.listdir(original):
			if "messages" in f:
				originalFile = os.path.join(original, f)
				destinationFile = os.path.join(destination, f + ".csv")
				convertFile(originalFile, destinationFile, referenceFile)

	if referenceFile:
		print
		print "=== Printing tree for the reference ==="
		printStringTree(getLanguageKeys(referenceFile), ".", "   ")
