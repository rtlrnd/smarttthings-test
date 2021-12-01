/**
 *  Copyright 2015 SmartThings
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
	definition (name: "ZXT600 Z-Wave Thermostat", namespace: "smartthings", author: "SmartThings") {
		capability "Actuator"
		capability "Temperature Measurement"
		//capability "Thermostat"
		capability "Thermostat Heating Setpoint"
		capability "Thermostat Cooling Setpoint"
		// capability "Thermostat Operating State"
		capability "Thermostat Mode"
		capability "Thermostat Fan Mode"
		capability "Refresh"
		capability "Sensor"
		// capability "Health Check"
		
		// attribute "thermostatFanState", "string"

        attribute "learningPosition", "NUMBER"

		command "switchMode"
		command "switchFanMode"
		command "lowerHeatingSetpoint"
		command "raiseHeatingSetpoint"
		command "lowerCoolSetpoint"
		command "raiseCoolSetpoint"
        command "setRemoteCode"
		command "issueLearningCommand"
		command "poll"

		fingerprint deviceId: "0x08"
		fingerprint inClusters: "0x43,0x40,0x44,0x31", deviceJoinName: "Thermostat"
		// fingerprint mfr:"0039", prod:"0011", model:"0001", deviceJoinName: "Honeywell Thermostat" //Honeywell Z-Wave Thermostat
		// fingerprint mfr:"008B", prod:"5452", model:"5439", deviceJoinName: "Trane Thermostat" //Trane Thermostat
		// fingerprint mfr:"008B", prod:"5452", model:"5442", deviceJoinName: "Trane Thermostat" //Trane Thermostat
		fingerprint mfr:"5254", prod:"8B00", model:"8490", deviceJoinName: "ZXT-600 (2021)" //American Standard Thermostat
	}

	tiles {
		multiAttributeTile(name:"temperature", type:"generic", width:3, height:2, canChangeIcon: true) {
			tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {
				attributeState("temperature", label:'${currentValue}°', icon: "st.alarm.temperature.normal",
					backgroundColors:[
							// Celsius
							[value: 0, color: "#153591"],
							[value: 7, color: "#1e9cbb"],
							[value: 15, color: "#90d2a7"],
							[value: 23, color: "#44b621"],
							[value: 28, color: "#f1d801"],
							[value: 35, color: "#d04e00"],
							[value: 37, color: "#bc2323"],
							// Fahrenheit
							[value: 40, color: "#153591"],
							[value: 44, color: "#1e9cbb"],
							[value: 59, color: "#90d2a7"],
							[value: 74, color: "#44b621"],
							[value: 84, color: "#f1d801"],
							[value: 95, color: "#d04e00"],
							[value: 96, color: "#bc2323"]
					]
				)
			}
		}
		standardTile("mode", "device.thermostatMode", width:2, height:2, inactiveLabel: false, decoration: "flat") {
			state "off", action:"switchMode", nextState:"...", icon: "st.thermostat.heating-cooling-off"
			state "heat", action:"switchMode", nextState:"...", icon: "st.thermostat.heat"
			state "cool", action:"switchMode", nextState:"...", icon: "st.thermostat.cool"
			state "auto", action:"switchMode", nextState:"...", icon: "st.thermostat.auto"
			// state "emergency heat", action:"switchMode", nextState:"...", icon: "st.thermostat.emergency-heat"
			state "...", label: "Updating...",nextState:"...", backgroundColor:"#ffffff"
		}
		standardTile("fanMode", "device.thermostatFanMode", width:2, height:2, inactiveLabel: false, decoration: "flat") {
			state "auto", action:"switchFanMode", nextState:"...", icon: "st.thermostat.fan-auto"
			state "on", action:"switchFanMode", nextState:"...", icon: "st.thermostat.fan-on"
			state "circulate", action:"switchFanMode", nextState:"...", icon: "st.thermostat.fan-circulate"
			state "...", label: "Updating...", nextState:"...", backgroundColor:"#ffffff"
		}
		standardTile("lowerHeatingSetpoint", "device.heatingSetpoint", width:2, height:1, inactiveLabel: false, decoration: "flat") {
			state "heatingSetpoint", action:"lowerHeatingSetpoint", icon:"st.thermostat.thermostat-left"
		}
		valueTile("heatingSetpoint", "device.heatingSetpoint", width:2, height:1, inactiveLabel: false, decoration: "flat") {
			state "heatingSetpoint", label:'${currentValue}° heat', backgroundColor:"#ffffff"
		}
		standardTile("raiseHeatingSetpoint", "device.heatingSetpoint", width:2, height:1, inactiveLabel: false, decoration: "flat") {
			state "heatingSetpoint", action:"raiseHeatingSetpoint", icon:"st.thermostat.thermostat-right"
		}
		standardTile("lowerCoolSetpoint", "device.coolingSetpoint", width:2, height:1, inactiveLabel: false, decoration: "flat") {
			state "coolingSetpoint", action:"lowerCoolSetpoint", icon:"st.thermostat.thermostat-left"
		}
		valueTile("coolingSetpoint", "device.coolingSetpoint", width:2, height:1, inactiveLabel: false, decoration: "flat") {
			state "coolingSetpoint", label:'${currentValue}° cool', backgroundColor:"#ffffff"
		}
		standardTile("raiseCoolSetpoint", "device.heatingSetpoint", width:2, height:1, inactiveLabel: false, decoration: "flat") {
			state "heatingSetpoint", action:"raiseCoolSetpoint", icon:"st.thermostat.thermostat-right"
		}
		// standardTile("thermostatOperatingState", "device.thermostatOperatingState", width: 2, height:1, decoration: "flat") {
		// 	state "thermostatOperatingState", label:'${currentValue}', backgroundColor:"#ffffff"
		// }

               // Configure button.  Syncronize the device capabilities that the UI provides
        // standardTile("configure", "device.configure", inactiveLabel: false) {
        //     state "configure", label:'Config', action:"configuration.configure", 
        //           icon:"st.Entertainment.entertainment15", backgroundColor:"#00bfff"
        // }
        // standardTile("configure", "device.configure", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
		// 	state "configure", label:'', action:"configuration.configure", icon:"st.secondary.configure"
	  	// }    

		// standardTile("refresh", "device.thermostatMode", width:2, height:1, inactiveLabel: false, decoration: "flat") {
		// 	state "default", action:"refresh.refresh", icon:"st.secondary.refresh"
		// }
		main "temperature"
		// details(["temperature", "lowerHeatingSetpoint", "heatingSetpoint", "raiseHeatingSetpoint", "lowerCoolSetpoint",
		// 		"coolingSetpoint", "raiseCoolSetpoint", "mode", "fanMode", "thermostatOperatingState", "refresh"])
        details(["temperature", "lowerHeatingSetpoint", "heatingSetpoint", "raiseHeatingSetpoint", "lowerCoolSetpoint",
				"coolingSetpoint", "raiseCoolSetpoint", "mode", "fanMode"])
	}

    preferences {
    	input description: "Press Configure after making any changes", displayDuringSetup: true,
            type: "paragraph", element: "paragraph"
    	input("remoteCode", "number", title: "Remote Code", description: "The number of the remote to emulate")
    	// input("tempOffset", "enum", title: "Temp correction offset?", options: ["-5","-4","-3","-2","-1","0","1","2","3","4","5"])
    	// input("swingSetting", "number", title: "Swing ON/OFF", description: "Trigger AC Swing ON/OFF")
		// input("learningPositionControl", "number", title: "Learning Position", description: "AC learning position")
		// input("shortName", "string", title: "Short Name for Home Page Temp Icon", description: "Short Name:")
	}
}

def installed() {
	// // Configure device
	// def cmds = [new physicalgraph.device.HubAction(zwave.associationV1.associationSet(groupingIdentifier:1, nodeId:[zwaveHubNodeId]).format()),
	// 		new physicalgraph.device.HubAction(zwave.manufacturerSpecificV2.manufacturerSpecificGet().format())]
	// sendHubCommand(cmds)
	// runIn(3, "initialize", [overwrite: true])  // Allow configure command to be sent and acknowledged before proceeding
	log.debug "ZXT-600 installed()"
}

def updated() {
	// If not set update ManufacturerSpecific data

	unsubscribe()
	
    log.debug "Updated Once"

	if (!getDataValue("manufacturer")) {
		sendHubCommand(new physicalgraph.device.HubAction(zwave.manufacturerSpecificV2.manufacturerSpecificGet().format()))
		runIn(2, "initialize", [overwrite: true])  // Allow configure command to be sent and acknowledged before proceeding
    } else {
        // Only support 1 configuration "set" at the same time.
        log.debug "before initialize()"
        log.debug "remoteCode Setting value is $settings.remoteCode"
        log.debug "tempOffset Setting value is $settings.tempOffset"
        log.debug "shortName Setting value is $settings.shortName"

        if(settings.remoteCode != state.previousSetCode) {
            log.debug "Update remoteCode"
            setRemoteCode()
        } else if (settings.tempOffset != state.previoustempoffset){
            log.debug "Update Temperature offset"
            setTempOffset()
        } else if (settings.learningPosition != state.previoustempoffset){
            log.debug "Update Temperature offset"
            setSwing()
        } else {
            log.debug "this is remote code with cancel"
            initialize()
        }        
        // doZwaveUpdateTask()
	}
}

// physicalgraph.zwave.commands.configurationv1.ConfigurationReport cmd

def configure() {
    // update the device's remote code to ensure it provides proper mode info
    // The reason why remote set code process is excluded from the below sequence is 
    // the time consuming is up to 12s based on 8490 characteristic.
    setRemoteCode()
}

def initialize() {
	// Device-Watch simply pings if no device events received for 32min(checkInterval)
	sendEvent(name: "checkInterval", value: 2 * 15 * 60 + 2 * 60, displayed: false, data: [protocol: "zwave", hubHardwareId: device.hub.hardwareID])
	unschedule()
	if (getDataValue("manufacturer") != "Honeywell") {
		runEvery5Minutes("poll")  // This is not necessary for Honeywell Z-wave, but could be for other Z-wave thermostats
	}
    log.debug "Do pollDevice"
	pollDevice()
}

def doZwaveUpdateTask() {

    switch (cmd.parameterNumber) {
        case commandParameters["remoteCode"]:
            log.debug "remote code configuration is triggered"
            break

        case commandParameters["tempOffsetParam"]:
            log.debug "temp offset configuration is triggered"
            break

        default:
            log.debug "Do nothing if not match"
            break;
    }

}


def parse(String description)
{
    // If the device sent an update, interpret it
    log.info "Parsing Description=$description"
    // 0X20=Basic - V1 supported
    // 0x27=Switch All - V1 supported
    // 0X31=Sensor Multilevel - V1 supported
    // 0X40=Thermostat Mode - V2 supported
    // 0x43=Thermostat Setpoint - V2 supported
    // 0x44=Thermostat Fan Mode - V2 supported
    // 0x70=Configuration - V1 supported
    // 0x72=Manufacturer Specific - V1 supported
    // 0x80=Battery - V1 supported
    // 0x86=Version - V1 supported

	def result = null
	if (description == "updated") {
	} else {
		def zwcmd = zwave.parse(description, [0x70:1, 0x40:1, 0x43:2, 0x44:2, 0x31: 1])
		if (zwcmd) {
			result = zwaveEvent(zwcmd)
		} else {
			log.debug "$device.displayName couldn't parse $description"
		}
	}
	if (!result) {
		return []
	}
	return [result]
}

// Event Generation
def zwaveEvent(physicalgraph.zwave.commands.thermostatsetpointv2.ThermostatSetpointReport cmd) {
	def cmdScale = cmd.scale == 1 ? "F" : "C"
	def setpoint = getTempInLocalScale(cmd.scaledValue, cmdScale)
	def unit = getTemperatureScale()
	switch (cmd.setpointType) {
		case 1:
			sendEvent(name: "heatingSetpoint", value: setpoint, unit: unit, displayed: false)
			updateThermostatSetpoint("heatingSetpoint", setpoint)
			break;
		case 2:
			sendEvent(name: "coolingSetpoint", value: setpoint, unit: unit, displayed: false)
			updateThermostatSetpoint("coolingSetpoint", setpoint)
			break;
		default:
			log.debug "unknown setpointType $cmd.setpointType"
			return
	}
	// So we can respond with same format
	state.size = cmd.size
	state.scale = cmd.scale
	state.precision = cmd.precision
	// Make sure return value is not result from above expresion
	return 0
}

def zwaveEvent(physicalgraph.zwave.commands.sensormultilevelv3.SensorMultilevelReport cmd) {
	def map = [:]
	if (cmd.sensorType == 1) {
		map.value = getTempInLocalScale(cmd.scaledSensorValue, cmd.scale == 1 ? "F" : "C")
		map.unit = getTemperatureScale()
		map.name = "temperature"
		updateThermostatSetpoint(null, null)
	} else if (cmd.sensorType == 5) {
		map.value = cmd.scaledSensorValue
		map.unit = "%"
		map.name = "humidity"
	}
	sendEvent(map)
}


// Command parameters
def getCommandParameters() { [
        "remoteCode": 27,
        "tempOffsetParam": 37,
        "oscillateSetting": 33,
        "learningMode": 25
]}

def zwaveEvent(physicalgraph.zwave.commands.configurationv1.ConfigurationReport cmd) {
    def map = [:]

    switch (cmd.parameterNumber) {
    // If the device is reporting its remote code
        case commandParameters["remoteCode"]:
            map.name = "remoteCode"
            map.displayed = false

            def short remoteCodeLow = cmd.configurationValue[1]
            def short remoteCodeHigh = cmd.configurationValue[0]
            map.value = (remoteCodeHigh << 8) + remoteCodeLow

            // Display configured code in tile
            log.debug "reported currentConfigCode=$map.value"
            sendEvent("name":"currentConfigCode", "value":map.value)

	        // Save latest data for comparsion with new "SET" command is received.
	        state.previousSetCode = map.value
            break

    // If the device is reporting its remote code
        case commandParameters["tempOffsetParam"]:
            map.name = "tempOffset"
            map.displayed = false

            def short offset = cmd.configurationValue[0]
            if (offset >= 0xFB) {
                // Hex FB-FF represent negative offsets FF=-1 - FB=-5
                offset = offset - 256
            }
            map.value = offset
            log.debug "reported offset=$map.value"
            // Display temp offset in tile
            sendEvent("name":"currentTempOffset", "value":map.value)
	        // Save latest data for comparsion with new "SET" command is received.
	        state.previoustempoffset = map.value
            break

    // If the device is reporting its oscillate mode
        case commandParameters["oscillateSetting"]:
            // determine if the device is oscillating
            def oscillateMode = (cmd.configurationValue[0] == 0) ? "off" : "on"

            //log.debug "Updated: Oscillate " + oscillateMode
            map.name = "swingMode"
            map.value = oscillateMode
            map.displayed = false

            map.isStateChange = oscillateMode != getDataByName("swingMode")

            log.debug "reported swing mode = oscillateMode"
            // Store and report the oscillate mode
            updateState("swingMode", oscillateMode)
		//	state.previoustempoffset = map.value
            break
        default:
            log.warn "Unknown configuration report cmd.parameterNumber"
            break;
    }

    map
}


def zwaveEvent(physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeReport cmd) {
	def map = [name: "thermostatMode", data:[supportedThermostatModes: state.supportedModes]]
	switch (cmd.mode) {
		case physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeReport.MODE_OFF:
			map.value = "off"
			break
		case physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeReport.MODE_HEAT:
			map.value = "heat"
			break
		case physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeReport.MODE_AUXILIARY_HEAT:
			map.value = "emergency heat"
			break
		case physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeReport.MODE_COOL:
			map.value = "cool"
			break
		case physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeReport.MODE_AUTO:
			map.value = "auto"
			break
	}
	sendEvent(map)
	updateThermostatSetpoint(null, null)
}

def zwaveEvent(physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeReport cmd) {
	def map = [name: "thermostatFanMode", data:[supportedThermostatFanModes: state.supportedFanModes]]
	switch (cmd.fanMode) {
		case physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeReport.FAN_MODE_AUTO_LOW:
			map.value = "auto"
			break
		case physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeReport.FAN_MODE_LOW:
			map.value = "on"
			break
		case physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeReport.FAN_MODE_CIRCULATION:
			map.value = "circulate"
			break
	}
	sendEvent(map)
}

def zwaveEvent(physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeSupportedReport cmd) {
	def supportedModes = []
	if(cmd.off) { supportedModes << "off" }
	if(cmd.heat) { supportedModes << "heat" }
	if(cmd.cool) { supportedModes << "cool" }
	if(cmd.auto) { supportedModes << "auto" }
	//if(cmd.auxiliaryemergencyHeat) { supportedModes << "emergency heat" }

	state.supportedModes = supportedModes
	sendEvent(name: "supportedThermostatModes", value: supportedModes, displayed: false)
}

def zwaveEvent(physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeSupportedReport cmd) {
	def supportedFanModes = []
	if(cmd.auto) { supportedFanModes << "auto" }
	if(cmd.circulation) { supportedFanModes << "circulate" }
	if(cmd.low) { supportedFanModes << "on" }

	state.supportedFanModes = supportedFanModes
	sendEvent(name: "supportedThermostatFanModes", value: supportedFanModes, displayed: false)
}

def zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd) {
	if (cmd.manufacturerName) {
		updateDataValue("manufacturer", cmd.manufacturerName)
	}
	if (cmd.productTypeId) {
		updateDataValue("productTypeId", cmd.productTypeId.toString())
	}
	if (cmd.productId) {
		updateDataValue("productId", cmd.productId.toString())
	}
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd) {
	log.debug "Zwave BasicReport: $cmd"
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	log.warn "Unexpected zwave command $cmd"
}

// Command Implementations
def poll() {
	// Call refresh which will cap the polling to once every 2 minutes
	refresh()
}

def refresh() {
	// Only allow refresh every 4 minutes to prevent flooding the Zwave network
	def timeNow = now()
	if (!state.refreshTriggeredAt || (4 * 60 * 1000 < (timeNow - state.refreshTriggeredAt))) {
		state.refreshTriggeredAt = timeNow
		if (!state.longRefreshTriggeredAt || (48 * 60 * 60 * 1000 < (timeNow - state.longRefreshTriggeredAt))) {
			state.longRefreshTriggeredAt = timeNow
			// poll supported modes once every 2 days: they're not likely to change
			runIn(10, "longPollDevice", [overwrite: true])
		}
		// use runIn with overwrite to prevent multiple DTH instances run before state.refreshTriggeredAt has been saved
		runIn(2, "pollDevice", [overwrite: true])
	}
}

def pollDevice() {
	def cmds = []
	cmds << new physicalgraph.device.HubAction(zwave.thermostatModeV2.thermostatModeGet().format())
	cmds << new physicalgraph.device.HubAction(zwave.thermostatFanModeV3.thermostatFanModeGet().format())
	cmds << new physicalgraph.device.HubAction(zwave.sensorMultilevelV2.sensorMultilevelGet().format()) // current temperature
	// cmds << new physicalgraph.device.HubAction(zwave.thermostatOperatingStateV1.thermostatOperatingStateGet().format())
	cmds << new physicalgraph.device.HubAction(zwave.thermostatSetpointV1.thermostatSetpointGet(setpointType: 1).format())
	cmds << new physicalgraph.device.HubAction(zwave.thermostatSetpointV1.thermostatSetpointGet(setpointType: 2).format())
	sendHubCommand(cmds)

// sendCommands(commands,1000)      // Does it works?


}

// these values aren't likely to change
def longPollDevice() {
	def cmds = []
	cmds << new physicalgraph.device.HubAction(zwave.thermostatModeV2.thermostatModeSupportedGet().format())
	cmds << new physicalgraph.device.HubAction(zwave.thermostatFanModeV3.thermostatFanModeSupportedGet().format())
	sendHubCommand(cmds)
}

def raiseHeatingSetpoint() {
	alterSetpoint(true, "heatingSetpoint")
}

def lowerHeatingSetpoint() {
	alterSetpoint(false, "heatingSetpoint")
}

def raiseCoolSetpoint() {
	alterSetpoint(true, "coolingSetpoint")
}

def lowerCoolSetpoint() {
	alterSetpoint(false, "coolingSetpoint")
}

// Adjusts nextHeatingSetpoint either .5° C/1° F) if raise true/false
def alterSetpoint(raise, setpoint) {
	def locationScale = getTemperatureScale()
	def deviceScale = (state.scale == 1) ? "F" : "C"
	def heatingSetpoint = getTempInLocalScale("heatingSetpoint")
	def coolingSetpoint = getTempInLocalScale("coolingSetpoint")
	def targetValue = (setpoint == "heatingSetpoint") ? heatingSetpoint : coolingSetpoint
	def delta = (locationScale == "F") ? 1 : 0.5
	targetValue += raise ? delta : - delta

	def data = enforceSetpointLimits(setpoint, [targetValue: targetValue, heatingSetpoint: heatingSetpoint, coolingSetpoint: coolingSetpoint])
	// update UI without waiting for the device to respond, this to give user a smoother UI experience
	// also, as runIn's have to overwrite and user can change heating/cooling setpoint separately separate runIn's have to be used
	if (data.targetHeatingSetpoint) {
		sendEvent("name": "heatingSetpoint", "value": getTempInLocalScale(data.targetHeatingSetpoint, deviceScale),
				unit: getTemperatureScale(), eventType: "ENTITY_UPDATE", displayed: false)
	}
	if (data.targetCoolingSetpoint) {
		sendEvent("name": "coolingSetpoint", "value": getTempInLocalScale(data.targetCoolingSetpoint, deviceScale),
				unit: getTemperatureScale(), eventType: "ENTITY_UPDATE", displayed: false)
	}
	if (data.targetHeatingSetpoint && data.targetCoolingSetpoint) {
		runIn(5, "updateHeatingSetpoint", [data: data, overwrite: true])
	} else if (setpoint == "heatingSetpoint" && data.targetHeatingSetpoint) {
		runIn(5, "updateHeatingSetpoint", [data: data, overwrite: true])
	} else if (setpoint == "coolingSetpoint" && data.targetCoolingSetpoint) {
		runIn(5, "updateCoolingSetpoint", [data: data, overwrite: true])
	}
}

def updateHeatingSetpoint(data) {
	updateSetpoints(data)
}

def updateCoolingSetpoint(data) {
	updateSetpoints(data)
}

def enforceSetpointLimits(setpoint, data) {
	def locationScale = getTemperatureScale() 
	// def minSetpoint = (setpoint == "heatingSetpoint") ? getTempInDeviceScale(40, "F") : getTempInDeviceScale(50, "F")
	// def maxSetpoint = (setpoint == "heatingSetpoint") ? getTempInDeviceScale(90, "F") : getTempInDeviceScale(99, "F")

	def minSetpoint = (setpoint == "heatingSetpoint") ?  getTempInDeviceScale(35, "F") :  getTempInDeviceScale(38, "F")
	def maxSetpoint = (setpoint == "heatingSetpoint") ?  getTempInDeviceScale(91, "F") :  getTempInDeviceScale(93, "F")

	def deadband = (state.scale == 1) ? 3 : 2  // 3°F, 2°C
	def targetValue = getTempInDeviceScale(data.targetValue, locationScale)
	def heatingSetpoint = null
	def coolingSetpoint = null
	// Enforce min/mix for setpoints
	if (targetValue > maxSetpoint) {
		targetValue = maxSetpoint
	} else if (targetValue < minSetpoint) {
		targetValue = minSetpoint
	}
	// Enforce 3 degrees F deadband between setpoints
	if (setpoint == "heatingSetpoint") {
		heatingSetpoint = targetValue 
		coolingSetpoint = (heatingSetpoint + deadband > getTempInDeviceScale(data.coolingSetpoint, locationScale)) ? heatingSetpoint + deadband : null
	}
	if (setpoint == "coolingSetpoint") {
		coolingSetpoint = targetValue
		heatingSetpoint = (coolingSetpoint - deadband < getTempInDeviceScale(data.heatingSetpoint, locationScale)) ? coolingSetpoint - deadband : null
	}
	return [targetHeatingSetpoint: heatingSetpoint, targetCoolingSetpoint: coolingSetpoint]
}

def setHeatingSetpoint(degrees) {
	if (degrees) {
		state.heatingSetpoint = degrees.toDouble()
		runIn(2, "updateSetpoints", [overwrite: true])
	}
}

def setCoolingSetpoint(degrees) {
	if (degrees) {
		state.coolingSetpoint = degrees.toDouble()
		runIn(2, "updateSetpoints", [overwrite: true])
	}
}

def updateSetpoints() {
	def deviceScale = (state.scale == 1) ? "F" : "C"
	def data = [targetHeatingSetpoint: null, targetCoolingSetpoint: null]
	def heatingSetpoint = getTempInLocalScale("heatingSetpoint")
	def coolingSetpoint = getTempInLocalScale("coolingSetpoint")
	if (state.heatingSetpoint) {
		data = enforceSetpointLimits("heatingSetpoint", [targetValue: state.heatingSetpoint,
				heatingSetpoint: heatingSetpoint, coolingSetpoint: coolingSetpoint])
	}
	if (state.coolingSetpoint) {
		heatingSetpoint = data.targetHeatingSetpoint ? getTempInLocalScale(data.targetHeatingSetpoint, deviceScale) : heatingSetpoint
		coolingSetpoint = data.targetCoolingSetpoint ? getTempInLocalScale(data.targetCoolingSetpoint, deviceScale) : coolingSetpoint
		data = enforceSetpointLimits("coolingSetpoint", [targetValue: state.coolingSetpoint,
				heatingSetpoint: heatingSetpoint, coolingSetpoint: coolingSetpoint])
		data.targetHeatingSetpoint = data.targetHeatingSetpoint ?: heatingSetpoint
	}
	state.heatingSetpoint = null
	state.coolingSetpoint = null
	updateSetpoints(data)
}

def updateSetpoints(data) {
	def cmds = []
	if (data.targetHeatingSetpoint) {
		cmds << zwave.thermostatSetpointV1.thermostatSetpointSet(setpointType: 1, scale: state.scale,
				precision: state.precision, scaledValue: data.targetHeatingSetpoint)
		cmds << zwave.thermostatSetpointV1.thermostatSetpointGet(setpointType: 1)
	}
	if (data.targetCoolingSetpoint) {
		cmds << zwave.thermostatSetpointV1.thermostatSetpointSet(setpointType: 2, scale: state.scale,
				precision: state.precision, scaledValue: data.targetCoolingSetpoint)
		cmds << zwave.thermostatSetpointV1.thermostatSetpointGet(setpointType: 2)
	}
	sendHubCommand(cmds, 1000)
}

// thermostatSetpoint is not displayed by any tile as it can't be predictable calculated due to
// the device's quirkiness but it is defined by the capability so it must be set, set it to the most likely value
def updateThermostatSetpoint(setpoint, value) {
	def scale = getTemperatureScale()
	def heatingSetpoint = (setpoint == "heatingSetpoint") ? value : getTempInLocalScale("heatingSetpoint")
	def coolingSetpoint = (setpoint == "coolingSetpoint") ? value : getTempInLocalScale("coolingSetpoint")
	def mode = device.currentValue("thermostatMode")
	def thermostatSetpoint = heatingSetpoint    // corresponds to (mode == "heat" || mode == "emergency heat")
	if (mode == "cool") {
		thermostatSetpoint = coolingSetpoint
	} else if (mode == "auto" || mode == "off") {
		// Set thermostatSetpoint to the setpoint closest to the current temperature
		def currentTemperature = getTempInLocalScale("temperature")
		if (currentTemperature > (heatingSetpoint + coolingSetpoint)/2) {
			thermostatSetpoint = coolingSetpoint
		}
	}
	sendEvent(name: "thermostatSetpoint", value: thermostatSetpoint, unit: getTemperatureScale())
}

/**
 * PING is used by Device-Watch in attempt to reach the Device
 * */
def ping() {
	log.debug "ping() called"
	// Just get Operating State there's no need to flood more commands
	sendHubCommand(new physicalgraph.device.HubAction(zwave.thermostatOperatingStateV1.thermostatOperatingStateGet().format()))
}

def switchMode() {
	def currentMode = device.currentValue("thermostatMode")
	def supportedModes = state.supportedModes
	// Old version of supportedModes was as string, make sure it gets updated
	if (supportedModes && supportedModes.size() && supportedModes[0].size() > 1) {
		def next = { supportedModes[supportedModes.indexOf(it) + 1] ?: supportedModes[0] }
		def nextMode = next(currentMode)
		runIn(2, "setGetThermostatMode", [data: [nextMode: nextMode], overwrite: true])
	} else {
		log.warn "supportedModes not defined"
		getSupportedModes()
	}
}

def switchToMode(nextMode) {
	def supportedModes = state.supportedModes
	// Old version of supportedModes was as string, make sure it gets updated
	if (supportedModes && supportedModes.size() && supportedModes[0].size() > 1) {
		if (supportedModes.contains(nextMode)) {
			runIn(2, "setGetThermostatMode", [data: [nextMode: nextMode], overwrite: true])
		} else {
			log.debug("ThermostatMode $nextMode is not supported by ${device.displayName}")
		}
	} else {
		log.warn "supportedModes not defined"
		getSupportedModes()
	}
}

def getSupportedModes() {
	def cmds = []
	cmds << new physicalgraph.device.HubAction(zwave.thermostatModeV2.thermostatModeSupportedGet().format())
	sendHubCommand(cmds)
}

def switchFanMode() {
	def currentMode = device.currentValue("thermostatFanMode")
	def supportedFanModes = state.supportedFanModes
	// Old version of supportedFanModes was as string, make sure it gets updated
	if (supportedFanModes && supportedFanModes.size() && supportedFanModes[0].size() > 1) {
		def next = { supportedFanModes[supportedFanModes.indexOf(it) + 1] ?: supportedFanModes[0] }
		def nextMode = next(currentMode)
		runIn(2, "setGetThermostatFanMode", [data: [nextMode: nextMode], overwrite: true])
	} else {
		log.warn "supportedFanModes not defined"
		getSupportedFanModes()
	}
}

def switchToFanMode(nextMode) {
	def supportedFanModes = state.supportedFanModes
	// Old version of supportedFanModes was as string, make sure it gets updated
	if (supportedFanModes && supportedFanModes.size() && supportedFanModes[0].size() > 1) {
		if (supportedFanModes.contains(nextMode)) {
			runIn(2, "setGetThermostatFanMode", [data: [nextMode: nextMode], overwrite: true])
		} else {
			log.debug("FanMode $nextMode is not supported by ${device.displayName}")
		}
	} else {
		log.warn "supportedFanModes not defined"
		getSupportedFanModes()
	}
}

def getSupportedFanModes() {
	def cmds = [new physicalgraph.device.HubAction(zwave.thermostatFanModeV3.thermostatFanModeSupportedGet().format())]
	sendHubCommand(cmds)
}

def getModeMap() { [
	"off": 0,
	"heat": 1,
	"cool": 2,
	"auto": 3,
	"emergency heat": 4
]}

def setThermostatMode(String value) {
	switchToMode(value)
}

def setGetThermostatMode(data) {
	def cmds = [new physicalgraph.device.HubAction(zwave.thermostatModeV2.thermostatModeSet(mode: modeMap[data.nextMode]).format()),
			new physicalgraph.device.HubAction(zwave.thermostatModeV2.thermostatModeGet().format())]
	sendHubCommand(cmds)
}

def getFanModeMap() { [
	"auto": 0,
	"on": 1,
	"circulate": 6
]}

def setThermostatFanMode(String value) {
	switchToFanMode(value)
}

def setGetThermostatFanMode(data) {
	def cmds = [new physicalgraph.device.HubAction(zwave.thermostatFanModeV3.thermostatFanModeSet(fanMode: fanModeMap[data.nextMode]).format()),
			new physicalgraph.device.HubAction(zwave.thermostatFanModeV3.thermostatFanModeGet().format())]
	sendHubCommand(cmds)
}

def off() {
	switchToMode("off")
}

def heat() {
	switchToMode("heat")
}

def emergencyHeat() {
	switchToMode("emergency heat")
}

def cool() {
	switchToMode("cool")
}

def auto() {
	switchToMode("auto")
}

def fanOn() {
	switchToFanMode("on")
}

def fanAuto() {
	switchToFanMode("auto")
}

def fanCirculate() {
	switchToFanMode("circulate")
}

// Get stored temperature from currentState in current local scale
def getTempInLocalScale(state) {
	def temp = device.currentState(state)
	if (temp && temp.value && temp.unit) {
		return getTempInLocalScale(temp.value.toBigDecimal(), temp.unit)
	}
	return 0
}

// get/convert temperature to current local scale
def getTempInLocalScale(temp, scale) {
	if (temp && scale) {
		def scaledTemp = convertTemperatureIfNeeded(temp.toBigDecimal(), scale).toDouble()
		return (getTemperatureScale() == "F" ? scaledTemp.round(0).toInteger() : roundC(scaledTemp))
	}
	return 0
}

def getTempInDeviceScale(state) {
	def temp = device.currentState(state)
	if (temp && temp.value && temp.unit) {
		return getTempInDeviceScale(temp.value.toBigDecimal(), temp.unit)
	}
	return 0
}

def getTempInDeviceScale(temp, scale) {
	if (temp && scale) {
		def deviceScale = (state.scale == 1) ? "F" : "C"
		return (deviceScale == scale) ? temp :
				(deviceScale == "F" ? celsiusToFahrenheit(temp).toDouble().round(0).toInteger() : roundC(fahrenheitToCelsius(temp)))
	}
	return 0
}

def roundC (tempC) {
	return (Math.round(tempC.toDouble() * 2))/2
}


def setTempOffset() {
    def cmds = []
    // Load the user's remote code setting
    def tempOffsetVal = tempOffset == null ? 0 : tempOffset.toInteger()
    // Convert negative values into hex value for this param -1 = 0xFF -5 = 0xFB
    if (tempOffsetVal < 0) {
        tempOffsetVal = 256 + tempOffsetVal
    }

    def configArray = [tempOffsetVal]
    log.debug "TempOffset: ${tempOffsetVal}"
    cmds << zwave.configurationV1.configurationSet(configurationValue: configArray, parameterNumber: commandParameters["tempOffsetParam"], size: 1)
    cmds << zwave.configurationV1.configurationGet(parameterNumber: commandParameters["tempOffsetParam"])
    sendHubCommand(cmds, 1000)
}

// Set Remote Code
// tell the ZXT-600 what remote code to use when communicating with the A/C
def setRemoteCode() {
    // Load the user's remote code setting
    def cmds = []

    def remoteCodeVal = remoteCode.toInteger()

    // Divide the remote code into a 2 byte value
    def short remoteCodeLow = remoteCodeVal & 0xFF
    def short remoteCodeHigh = (remoteCodeVal >> 8) & 0xFF
    def remoteBytes = [remoteCodeHigh, remoteCodeLow]

    log.debug "New Remote Code: ${remoteBytes}"

    cmds << zwave.configurationV1.configurationSet(configurationValue: remoteBytes, parameterNumber: commandParameters["remoteCode"], size: 2)
    cmds << zwave.configurationV1.configurationGet(parameterNumber: commandParameters["remoteCode"])
    sendHubCommand(cmds, 12000)
}


// Switch Fan Oscillate
// Toggle fan oscillation on and off
def switchFanOscillate() {
    // Load the current swingmode and invert it (Off becomes true, On becomes false)
    def swingMode = (getDataByName("swingMode") == "off")

    // Make the new swingMode happen
    setFanOscillate(swingMode)
}

def swingModeOn() {
    log.debug "Setting Swing mode On"
    setFanOscillate(true)
}

def swingModeOff() {
    log.debug "Setting Swing mode Off"
    setFanOscillate(false)
}

// Set Fan Oscillate
// Set the fan oscillation to On (swingMode == true) or Off (swingMode == false)
def setFanOscillate(swingMode) {
    // Convert the swing mode requested to 1 for on, 0 for off
    def swingValue = swingMode ? 1 : 0

    delayBetween ([
            // Command the new Swing Mode
            zwave.configurationV1.configurationSet(configurationValue: [swingValue],
                    parameterNumber: commandParameters["oscillateSetting"], size: 1).format(),
            // Request the device's swing mode to make sure the new setting was accepted
            zwave.configurationV1.configurationGet(parameterNumber: commandParameters["oscillateSetting"]).format()
    ], 100)
}


/*
Based on ZXT-600 user manual (IR Learning Mapping Table, Parameter number 25)

Parameter Value    |          Thermostat Command & IR Setting
(Storage Location) | Storage in Celsius Unit | Storage in Fahrenheit Unit

0| OFF | OFF
1| ON (RESUME) | ON (RESUME)
2| 17°C COOL | 63°F COOL
3| 18°C COOL | 64°F COOL
4| 19°C COOL | 66°F or 67°F COOL
5| 20°C COOL | 68°F or 69°F COOL
6| 21°C COOL | 70°F or 71°F COOL
7| 22°C COOL | 72°F or 73°F COOL
8| 23°C COOL | 74°F or 75°F COOL
9| 24°C COOL | 76°F COOL
10| 25°C COOL | 77°F or 78°F COOL
11| 26°C COOL | 79°F or 80°F COOL
12| 27°C COOL | 81°F or 82°F COOL
13| 28°C COOL | 83°F or 84°F COOL
14| 29°C COOL | 85°F COOL
15| 30°C COOL | 86°F COOL
16| 17°C HEAT | 63°F HEAT
17| 18°C HEAT | 64°F HEAT
18| 19°C HEAT | 66°F or 67°F HEAT
19| 20°C HEAT | 68°F or 69°F HEAT
20| 21°C HEAT | 70°F or 71°F HEAT
21| 22°C HEAT | 72°F or 73°F HEAT
22| 23°C HEAT | 74°F or 75°F HEAT
23| 24°C HEAT | 76°F HEAT
24| 25°C HEAT | 77°F or 78°F HEAT
25| 26°C HEAT | 79°F or 80°F HEAT
26| 27°C HEAT | 81°F or 82°F HEAT
27| 28°C HEAT | 83°F or 84°F HEAT
28| 29°C HEAT | 85°F HEAT
29| 30°C HEAT | 86°F HEAT
30| DRY MODE  |DRY MODE
31| AUTO MODE | AUTO MODE
32| FAN MODE | FAN MODE

*/
def setLearningPosition(position) {
    log.debug "Setting learning postition: $position"
    sendEvent("name":"learningPosition", "value":position)
    def ctemp = 0
    if (position < 16) {
        ctemp=position+15
    } else {
        ctemp=position+1
    }
    def ftempLow=(Math.ceil(((ctemp*9)/5)+32)).toInteger()
    def ftempHigh=ftempLow+1
    def ftempLower = ftempLow-1
    def positionTemp = "not set"
    switch (position) {
        case 0:
            positionTemp = 'Off'
            break
        case 1:
            positionTemp = 'On(resume)'
            break
        case [3,4]:
        	positionTemp = "cool ${ctemp}C ${ftempLower}-${ftempLow}F"
            break
        case [5,6,7,8,10,11,12,13]:            
            positionTemp = "cool ${ctemp}C ${ftempLow}-${ftempHigh}F"
            break
        case [2,9,14,15]:
            positionTemp = "cool ${ctemp}C ${ftempLow}F"
            break
        case [17,18]:
        	positionTemp = "heat ${ctemp}C ${ftempLower}-${ftempLow}F"
            break
        case [19,20,21,22,24,25,26,27]:
            positionTemp = "heat ${ctemp}C ${ftempLow}-${ftempHigh}F"
            break
        case [16,23,28,29]:
            positionTemp = "heat ${ctemp}C ${ftempLow}F"
            break
        case 30:
            positionTemp = 'Dry mode'
            break
        case 31:
            positionTemp = 'Auto mode'
            break
        case 32:
            positionTemp = 'Fan mode'
            break    
        default:
            positionTemp = 'Invalid'
            break
    }   
    log.debug "Ready to learn ${positionTemp}"
    sendEvent("name":"learningPositionTemp", "value":positionTemp)
}

def issueLearningCommand() {
    def position = 0
    if (device.currentValue("learningPosition"))
    	position = device.currentValue("learningPosition").toInteger()
    log.debug "Issue Learning Command pressed Position Currently: $position"

    def positionConfigArray = [position]

    log.debug "Position Config Array: ${positionConfigArray}"

    secureLearnConfigSequence ([
            // Send the new remote code
            zwave.configurationV1.configurationSet(configurationValue: positionConfigArray,
                    parameterNumber: commandParameters["learningMode"], size: 1)
    ])
}

// It is hard to define as IR signal cannot be expected within IR capturing period
private secureLearnConfigSequence(commands, delay=3000) {
	delayBetween(commands.collect{ secure(it) }, delay)
}
