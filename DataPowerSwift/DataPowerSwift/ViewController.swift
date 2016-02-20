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

import UIKit
import IBMMobileFirstPlatformFoundation

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        WLClient.sharedInstance().registerChallengeHandler(DataPowerChallengeHandler(vc: self))
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func getData(sender: UIButton) {
        
        let invocationData = WLProcedureInvocationData(adapterName: "Protected", procedureName: "getSecretData")
        let invokeListener = InvokeListener(vc: self)
        WLClient.sharedInstance().invokeProcedure(invocationData, withDelegate: invokeListener)
        
    }
    @IBAction func logout(sender: UIButton) {
        WLClient.sharedInstance().logout("WASLTPARealm", withDelegate: LogoutListener(vc: self))
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?){
        let loginView = segue.destinationViewController as! LoginViewController
        loginView.challengeHandler = sender as? DataPowerChallengeHandler
    }
    
    func alert(alertTitle: String, msg:String){
        let alert = UIAlertController(title: alertTitle, message: msg, preferredStyle: .Alert)
        alert.addAction(UIAlertAction(title: "OK",
            style: .Default,
            handler: nil))
        self.presentViewController(alert,
            animated: true,
            completion: nil)

    }

}

