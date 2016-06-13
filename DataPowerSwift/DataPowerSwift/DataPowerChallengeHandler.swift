//
//  DataPowerChallengeHandler.swift
//  DataPowerSwift
//
//  Created by Nathan Hazout on 27/04/2016.
//  Copyright © 2016 Nathan Hazout. All rights reserved.
//

import Foundation
import IBMMobileFirstPlatformFoundation

class DataPowerChallengeHandler : GatewayChallengeHandler {
    static let sharedInstance = DataPowerChallengeHandler()
    let myGateway = "myGateway"
    
    override init() {
        super.init(gatewayName: myGateway)
    }
    
    override func canHandleResponse(response: WLResponse!) -> Bool {
        print("DataPowerChallengeHandler isCustomResponse")
        if response != nil && response.responseText != nil{
            if response.responseText.lowercaseString.rangeOfString("j_security_check") != nil{
                NSLog("Detected j_security_check string - returns true")
                return true
            }
        }
        return false
    }
    
    override func handleChallenge(response: WLResponse!) {
        print("DataPowerChallengeHandler handleChallenge")
        NSNotificationCenter.defaultCenter().postNotificationName(ACTION_CHALLENGE_RECEIVED, object: self)
    }
    
    override func onSuccess(response: WLResponse!) {
        print("DataPowerChallengeHandler onSuccess")
        NSNotificationCenter.defaultCenter().postNotificationName(ACTION_CHALLENGE_SUCCESS, object: self)
        submitSuccess(response)
    }
    
    override func onFailure(response: WLFailResponse!) {
        print("DataPowerChallengeHandler onFailure")
        cancel()
    }
    
    func login(username: String, password: String){
        self.submitLoginForm("/j_security_check", requestParameters: ["j_username":username, "j_password": password], requestHeaders: nil, requestTimeoutInMilliSeconds: 0, requestMethod: "POST")
    }
    
}