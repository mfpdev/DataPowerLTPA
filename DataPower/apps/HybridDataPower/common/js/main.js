/**
* Copyright 2015 IBM Corp.
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

var busyIndicator = null;
var DataPowerChallengeHandler = WL.Client.createChallengeHandler();

function wlCommonInit(){
	busyIndicator = new WL.BusyIndicator();
}

function getData(){
	busyIndicator.show();
	$('#result').empty();
	$('#auth').hide();
	
	var invocationData = {
		    adapter : 'Protected',
		    procedure : 'getSecretData',
		};
	var options = {
		    onSuccess : loadSuccess,
		    onFailure : loadFailure,
		    invocationContext: {}
		};
	WL.Client.invokeProcedure(invocationData, options);
}


function loadSuccess(result){
	WL.Logger.debug("Data retrieve success");
	busyIndicator.hide();
	$('#result').html(JSON.stringify(result.responseJSON));
}

function loadFailure(result){
	WL.Logger.error("Data retrieve failure");
	busyIndicator.hide();
	WL.SimpleDialog.show("DataPower", "Service not available. Try again later.", 
			[{
				text : 'Reload',
				handler : WL.Client.reloadApp 
			},
			{
				text: 'Close',
				handler : function() {}
			}]
		);
}

//Challenge Handler
DataPowerChallengeHandler.isCustomResponse = function(response){
	if (!response || response.responseText === null) {
        return false;
    }
    var indicatorIdx = response.responseText.search('j_security_check');
     
    if (indicatorIdx >= 0){
        return true;
    } 
    
    return false;
};

DataPowerChallengeHandler.handleChallenge = function(response){
	$('#result').hide();
	$('#auth').show();
	busyIndicator.hide();
};

DataPowerChallengeHandler.submitLoginFormCallback = function(response) {
    var isLoginFormResponse = DataPowerChallengeHandler.isCustomResponse(response);
    if (isLoginFormResponse){
    	DataPowerChallengeHandler.handleChallenge(response);
    } else {
        $('#result').show();
        $('#auth').hide();
        DataPowerChallengeHandler.submitSuccess();
    }
};

$('#AuthSubmitButton').bind('click', function () {
	busyIndicator.show();
    var reqURL = '/j_security_check';
    var options = {};
    options.parameters = {
        j_username : $('#AuthUsername').val(),
        j_password : $('#AuthPassword').val()
    };
    options.headers = {};
    DataPowerChallengeHandler.submitLoginForm(reqURL, options, DataPowerChallengeHandler.submitLoginFormCallback);
});

$('#logout').on('click',function(){
	WL.Client.logout('WASLTPARealm', {onSuccess: function() {
		alert("Success logout");
		}
	});
});

