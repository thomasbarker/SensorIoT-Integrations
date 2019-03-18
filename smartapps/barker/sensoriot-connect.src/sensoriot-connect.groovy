/**
 *  SensorIoT Connect
 *
 *  Copyright 2019 Tom Barker
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "SensorIoT Connect",
    namespace: "Barker",
    author: "Tom Barker",
    description: "SensorIoT connection app",
    category: "SmartThings Labs",
    iconUrl: "http://cdn.device-icons.smartthings.com/Electronics/electronics18-icn.png",
    iconX2Url: "http://cdn.device-icons.smartthings.com/Electronics/electronics18-icn.png",
    iconX3Url: "http://cdn.device-icons.smartthings.com/Electronics/electronics18-icn.png") {
    appSetting "GatewayID"
}


preferences {
    section("Gateway Setup") {
    }
    preferences {
        input("GatewayID", "text", title: "GatewayID", description: "Enter your SensorIoT Gateway ID", required: true, displayDuringSetup: true)
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def EnumerateAndAdd()
{
    // don't process if there are already children
    if(getChildDevices().size() > 0)
    return

    def params = [
        uri: "https://www.brintontech.com", //https://www.brintontech.com/SensorIoT/latest/1A9015
        path: "/SensorIoT/nodelist/${GatewayID}",
    ]

    try {
        httpGet(params) { resp ->

            def data = resp.data as String 
            def map = new groovy.json.JsonSlurper().parseText(data)

            for(def member : map) {
                def did = GatewayID + member
                def d = addChildDevice("Barker", "SensorIoT Sensor", did, null, ["label":"SensorIoT-${member}", completedSetup:true])

                d.setGatewayID(GatewayID)
                d.setSensorID(member)
            }
        }
    } catch (e) {
        log.error "something went wrong: $e"
    }

}

def updated() {
	log.debug "Updated with settings: ${settings}"
    initialize()
}

def initialize() {
    EnumerateAndAdd()
    UpdateAllChildren()
    runEvery5Minutes(UpdateAllChildren)
}

def UpdateAllChildren(){
	log.debug "UpdateAllChildren"
    def devices = getChildDevices()

    def params = [
        uri: "https://www.brintontech.com", //https://www.brintontech.com/SensorIoT/latest/1A9015
        path: "/SensorIoT/latest/${GatewayID}", //"/SensorIoT/latest/1A9015",
    ]

    try {
        httpGet(params) { resp ->

            def data = resp.data as String 
            def map = new groovy.json.JsonSlurper().parseText(data)

            devices.each { child ->
                child.processMap(map)
            }

        }
    } catch (e) {
        log.error "something went wrong: $e"
    }

}