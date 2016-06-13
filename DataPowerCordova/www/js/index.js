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

var Messages = {
  // Add here your messages for the default language.
  // Generate a similar file with a language suffix containing the translated messages.
  // key1 : message1,
};

var wlInitOptions = {
  // Options to initialize with the WL.Client object.
  // For initialization options please refer to IBM MobileFirst Platform Foundation Knowledge Center.
};

var useResourceRequest = true;

var dataPowerChallengeHandler;
// Called automatically after MFP framework initialization by WL.Client.init(wlInitOptions).
function wlCommonInit() {

  //MFP APIs should only be called within wlCommonInit() or after it has been called, to ensure that the APIs have loaded properly
  document.getElementById("load").addEventListener("click", getData);
  dataPowerChallengeHandler = DataPowerChallengeHandler();
}

function getData() {
  document.getElementById('result').innerHTML = "";
  document.getElementById('auth').style.display = 'none';
  if(useResourceRequest){
    var resourceRequest = new WLResourceRequest(
            "/adapters/ResourceAdapter/balance",
            WLResourceRequest.GET
        );
        resourceRequest.send().then(
          loadSuccess,
          loadFailure
        );
  } else {
    WLAuthorizationManager.obtainAccessToken("LtpaBasedSSO").then(
      function (accessToken) {
          WL.Logger.debug("obtainAccessToken onSuccess");
          alert("obtain success");
      },
      function (response) {
          WL.Logger.debug("obtainAccessToken onFailure: " + JSON.stringify(response));
          alert("obtain failure");
    });
  }

}

function loadSuccess(result){
	WL.Logger.debug("Data retrieve success");
	//busyIndicator.hide();
	document.getElementById('result').innerHTML = JSON.stringify(result.responseJSON);
}

function loadFailure(result){
	WL.Logger.error("Data retrieve failure");
	//busyIndicator.hide();
	alert("Service not available. Try again later.");
}
