//
//  ViewController.swift
//  DataPowerSwift
//
//  Created by Nathan Hazout on 27/04/2016.
//  Copyright © 2016 Nathan Hazout. All rights reserved.
//

import UIKit
import IBMMobileFirstPlatformFoundation

class ViewController: UIViewController {
    
    let useObtain = false

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(showLoginPage), name: ACTION_CHALLENGE_RECEIVED, object: nil)
    }
    
    override func viewDidDisappear(animated: Bool) {
        NSNotificationCenter.defaultCenter().removeObserver(self)
        super.viewDidDisappear(animated)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func getData(sender: UIButton) {
        if(self.useObtain) {
            WLAuthorizationManager.sharedInstance().obtainAccessTokenForScope("", withCompletionHandler: { (token, error) in
                if(error != nil){
                    self.showAlert("Error", message: error.description)
                } else {
                    self.showAlert("Success", message: "Token obtained")
                }
            })
            
        } else {
            let request = WLResourceRequest(
                URL: NSURL(string: "/adapters/ResourceAdapter/balance"),
                method: WLHttpMethodGet
            )
            request.sendWithCompletionHandler { (response, error) in
                if(error != nil){
                    self.showAlert("Error", message: error.description)
                } else {
                    self.showAlert("Success", message: response.responseText)
                }
            }
            //request.sendWithDelegate(RequestDelegate());
        }

    }
    
    @IBAction func logout(sender: UIButton) {
        WLAuthorizationManager.sharedInstance().logout("LtpaBasedSSO") { (error) in
            if(error != nil){
                self.showAlert("Error", message: "Failed to logout")
            } else {
                self.showAlert("Success", message: "Logged out")
            }
        }
    }
    
    func showAlert(title: String, message: String){
        let alert = UIAlertController(title: title, message: message, preferredStyle: .Alert)
        alert.addAction(UIAlertAction(title: "OK", style: .Default, handler: nil))
        dispatch_async(dispatch_get_main_queue()) { 
            self.presentViewController(alert, animated: true, completion: nil)
        }
    }
    
    func showLoginPage(){
        self.performSegueWithIdentifier("showLogin", sender: self)
    }
}

