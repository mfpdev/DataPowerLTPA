//
//  RequestDelegate.swift
//  DataPowerSwift
//
//  Created by Nathan Hazout on 19/05/2016.
//  Copyright © 2016 Nathan Hazout. All rights reserved.
//

import Foundation

class RequestDelegate : NSObject, NSURLSessionDataDelegate, NSURLSessionTaskDelegate {
    
    func URLSession(session: NSURLSession, dataTask: NSURLSessionDataTask, didReceiveData data: NSData) {
        let result = NSString(data: data, encoding: NSUTF8StringEncoding)
        print("Data received = \(result)")
    }
    
}