//
//  LoginViewController.swift
//  DataPowerSwift
//
//  Created by Nathan Hazout on 27/04/2016.
//  Copyright © 2016 Nathan Hazout. All rights reserved.
//

import UIKit
import IBMMobileFirstPlatformFoundation

class LoginViewController: UIViewController {

    @IBOutlet weak var username: UITextField!
    @IBOutlet weak var password: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.setHidesBackButton(true, animated:false);
        // Do view setup here.
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(closeLogin), name: ACTION_CHALLENGE_SUCCESS, object: nil)
    }
    
    override func viewDidDisappear(animated: Bool) {
        NSNotificationCenter.defaultCenter().removeObserver(self)
    }
    
    @IBAction func login(sender: UIButton) {
        DataPowerChallengeHandler.sharedInstance.login(self.username.text!, password: self.password.text!)
    }
    
    @IBAction func cancel(sender: UIButton) {
        self.closeLogin()
        DataPowerChallengeHandler.sharedInstance.cancel()
    }
    func closeLogin(){
        self.navigationController?.popViewControllerAnimated(true)
    }
    
}
