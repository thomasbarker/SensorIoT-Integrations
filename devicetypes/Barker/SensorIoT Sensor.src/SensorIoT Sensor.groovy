/**
 *  SensorIoT Sensor
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
metadata {
    definition(name: "SensorIoT Sensor", namespace: "Barker", author: "Tom Barker") {
        capability "Atmospheric Pressure Measurement"
        capability "Relative Humidity Measurement"
        capability "Temperature Measurement"
        capability "Battery"
        capability "Configuration"
        capability "Refresh"

        command "setGatewayID", ["string"]
        command "setSensorID", ["string"]
    }

	preferences {
        section {
            input title: "Temperature Offset", description: "This feature allows you to correct any temperature variations by selecting an offset. Ex: If your sensor consistently reports a temp that's 5 degrees too warm, you'd enter '-5'. If 3 degrees too cold, enter '+3'. Will be used on the next update and going forward", displayDuringSetup: false, type: "paragraph", element: "paragraph"
            input "tempOffset", "number", title: "Degrees", description: "Adjust temperature by this many degrees", range: "*..*", displayDuringSetup: false
        }
    }
    
    simulator {
    }

    tiles(scale: 2) {
        valueTile("temperature", "device.temperature", width: 6, height: 4) {
            state("default", unit: "F", label: '${currentValue}°',
                backgroundColors: [
                    [value: 32, color: "#153591"],
                    [value: 44, color: "#1e9cbb"],
                    [value: 59, color: "#90d2a7"],
                    [value: 74, color: "#44b621"],
                    [value: 84, color: "#f1d801"],
                    [value: 92, color: "#d04e00"],
                    [value: 98, color: "#bc2323"]
                ]
            )
        }

        valueTile("humidity", "device.humidity", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "humidity", label: '${currentValue}%', unit: "%", defaultState: true, icon: "st.Weather.weather12"
        }
        valueTile("battery", "device.battery", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "battery", label: '${currentValue}% Battery', unit: "%"
        }
        valueTile("pressure", "device.pressure", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "pressure", label: '${currentValue} Pressure', unit: ""
        }

    } // end tiles
}

def setGatewayID(def id) {
    state.gatewayid = id
}
def setSensorID(def id) {
    state.sensorid = id
}

def processMap(map) {
	log.debug "processMap"
    for (def member: map) {
        if (member.node_id == state.sensorid) {
            log.debug member.value
            switch (member.type) {
                case "P":
                    sendEvent(name: "pressure", value: Math.round(member.value))
                    break;
                case "H":
                    sendEvent(name: "humidity", value: Math.round(member.value))
                    break;
                case "F":
                    sendEvent(name: "temperature", value: Math.round(member.value)+(tempOffset==null?0:(int)tempOffset))
                    break;
                case "BAT":
                    sendEvent(name: "battery", value: Math.round(Math.min(((member.value - 1) * 200), 100.0)))
                    break;
            }
        }
    }
}
def refresh() {

}

def parse(String description) {
    log.debug "Parsing - '${description}'"
}


def updated() {
    log.debug "Updated with settings: ${settings}"
    initialize()
}

def initialize() {
}