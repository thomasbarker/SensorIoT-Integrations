# SensorIoT-Integrations

## Pre-req:

You may need to enable github integration with your smartthings IDE (Not sure though). If so, follow this guide:
https://docs.smartthings.com/en/latest/tools-and-ide/github-integration.html#step-1-enable-github-integration

Your IDE link should be:
  http://developer.smartthings.com/ 
  
  Click login and sign in with your account. Make sure to select your hub!


## Installation Instructions:

 1. Add the Github Repo to your IDE with the following settings:
  - Owner: _thomasbarker_
  - Name: _SensorIoT-Integrations_
  - Branch: _master_

 2. Under 'My Device Handlers' in the SmartThings IDE click <img src="http://community.smartthings.com/uploads/default/original/3X/c/6/c65c0c35b43c714b2d305af2d2e48b2bf2481a0f.png" width="165" height="41">
 3. Select _SensorIoT Integrations (master)_ from the drop-down menu.
 4. Select SensorIoT Sensor from the 'New' tab.
 5. Click the 'Publish' check-box in the bottom right.
 6. Click on 'Execute Update' 
 7. Go to the "My SmartApps" tab in the IDE and click <img src="http://community.smartthings.com/uploads/default/original/3X/c/6/c65c0c35b43c714b2d305af2d2e48b2bf2481a0f.png" width="165" height="41">
 8. Select _SensorIoT Integrations (master)_ from the drop-down menu.
 9. Select SensorIoT Connect SmartApp in the 'New' tab.  
 10. Click the 'Publish' check-box in the bottom right.
 11. Click on 'Execute Update' 
 12. Now, go into the SmartThings App on your phone/tablet - You should be using the 'classic' app and not the new one.
 13. Go to the 'Automation / SmartApps' tab and select 'Add a SmartApp'
 14. Select 'My Apps' from the bottom of the page
 15. Select "SensorIoT Connect (Connect)"
 16. Enter the gateway ID (uppercase, no idea if that is needed) and assign a name to the smart app (it will fail if you don't)
 17. Select 'Save' in the top right to install the app
 18. This should succeed and all the devices assigned to your gateway will now appear under 'devices' for use
 
 
