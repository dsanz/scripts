var fs = require('fs'),
	glob = require("glob");

function writeLicense(filename, fromFiles) {
	let fd;
	try {
		fd = fs.openSync(filename, 'w');
		fs.appendFileSync(fd, '<?xml version="1.0"?>' + '\n', 'utf8');
		fs.appendFileSync(fd, '<licenses>' + '\n', 'utf8');
		for (let index = 0; index < fromFiles.length; index++) {
			var lines = fs.readFileSync(fromFiles[index], 'utf-8').split('\n');
			// note we skip first and second lines
			for (let lx = 2; lx < lines.length; lx++) {
				fs.appendFileSync(fd, '\t' + lines[lx] + '\n', 'utf8');
			}
		}
		fs.appendFileSync(fd, '</licenses>' + '\n', 'utf8');
	}
	catch (err) {
		console.log(err);
	}
	finally {
		if (fd !== undefined) fs.closeSync(fd);
	}
}

writeLicense("out.xml", glob.sync("activation*.xml"))