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

var DataPowerChallengeHandler = function() {
    var dataPowerChallengeHandler = WL.Client.createGatewayChallengeHandler("LtpaBasedSSO");

    dataPowerChallengeHandler.canHandleResponse = function(response) {
        if (!response || response.responseText === null) {
            return false;
        }
        var indicatorIdx = response.responseText.search('j_security_check');

        if (indicatorIdx >= 0) {
            return true;
        }

        return false;
    };

    dataPowerChallengeHandler.handleChallenge = function(response) {
        document.getElementById('result').style.display = 'none';
        document.getElementById('auth').style.display = 'block';
    };

    dataPowerChallengeHandler.submitLoginFormCallback = function(response) {
        var isLoginFormResponse = dataPowerChallengeHandler.canHandleResponse(response);
        if (isLoginFormResponse) {
            dataPowerChallengeHandler.handleChallenge(response);
        } else {
            document.getElementById('result').style.display = 'block';
            document.getElementById('auth').style.display = 'none';
            dataPowerChallengeHandler.submitSuccess();
        }
    };

    document.getElementById("AuthSubmitButton").addEventListener("click", function() {
        var reqURL = '/j_security_check';
        var options = {};
        options.parameters = {
            j_username: document.getElementById('AuthUsername').value,
            j_password: document.getElementById('AuthPassword').value
        };
        dataPowerChallengeHandler.submitLoginForm(reqURL, options, dataPowerChallengeHandler.submitLoginFormCallback);
    });

    document.getElementById("logout").addEventListener("click", function() {

        WLAuthorizationManager.logout("LtpaBasedSSO").then(
            function() {
                WL.Logger.debug("logout onSuccess");
                alert("Success logout");
            },
            function(response) {
                WL.Logger.debug("logout onFailure: " + JSON.stringify(response));
            });
    });

    document.getElementById('AuthCancelButton').addEventListener("click",function(){
      document.getElementById('result').style.display = 'block';
      document.getElementById('auth').style.display = 'none';
      dataPowerChallengeHandler.cancel();
    });

    return dataPowerChallengeHandler;
};
