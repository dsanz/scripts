# Activation keys merger
## How to install
* Install node.js in your machine (https://nodejs.org/en/download/). Wait for installation to finish before moving forward.
* Open a terminal and run the following command: `npm install glob`
* Clone this repo, or just [download `akm.js` file](https://raw.githubusercontent.com/dsanz/scripts/master/activation-key-merger/akm.js), or: 
	* Open akm.js above link in a new tab
	* Click on "raw" button
	* Right click → Save as
* Optional steps for windows (this will avoid using the terminal in the future): 
	* Once downloaded right click in akm.js from file explorer. Go to "Open with" 
	* Associate .js files with node permanently

## Usage instructions
* Open a terminal, go to the folder where `akm.js` is
* Copy activation key files you'd like to merge in that folder 
	* Ensure all license files start with "activation" and have .xml extension
* If you associated .js files with node, just double click in akm.js. Otherwise: 
	* Open a terminal. Go to that folder
	* run `node akm.js`
* Check the file `out.xml`. It contains the merged activation key  

