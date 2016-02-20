/**
 * Copyright 2016 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import Foundation
import IBMMobileFirstPlatformFoundation

class DataPowerChallengeHandler : ChallengeHandler{
    
    let vc : ViewController
    
    init(vc: ViewController){
        self.vc = vc
        super.init(realm: "WASLTPARealm")
    }
    
    override func isCustomResponse(response: WLResponse!) -> Bool {
        if response != nil && response.responseText != nil{
            if response.responseText.lowercaseString.rangeOfString("j_security_check") != nil{
                NSLog("Detected j_security_check string - returns true")
                return true
            }
        }
        return false
    }
    
    override func handleChallenge(response: WLResponse!) {
        NSLog(response.description)
        vc.performSegueWithIdentifier("login", sender: self)
    }
    
    override func onSuccess(response: WLResponse!) {
        NSLog("Challenge succeeded")
        self.vc.dismissViewControllerAnimated(true, completion: nil)
        self.submitSuccess(response)
    }
    
    override func onFailure(response: WLFailResponse!) {
        NSLog("Challenge failed")
        self.vc.dismissViewControllerAnimated(true, completion: nil)
        self.submitFailure(response)
    }
}