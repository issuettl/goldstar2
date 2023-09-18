<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ko>
<head>
    <meta charset="UTF-8">
    <title>LGE.COM | LG전자</title>
    <link rel="shortcut icon" href="/lg5-common/images/favicon.ico">
</head>
<body>
<script>
    var viewParams = JSON.parse('{"additionalUris":${additionalUris},"additionalLoginTimeout":1000}');
    var ssoId = "${ssoId}";
    var amValue = '{"ci":"${ci}","lgecom_mbrno":"${lgecomMbrNo}","sso_id":"${ssoId}", "lgecom_svcuserid" : "${lgecomSvcUserId}", "id_tp_code" : "${idTpCode}", "src_svc_code" : "${src_svc_code}"}';
    //console.log(amValue);

    var isApp = function(){
        return /LGEAPP|lgeapp\/[0-9\.]+$/.test(navigator.userAgent);
    }
    var os_type_code = "a";
    if (/iPhone|iPad|iPod/i.test(navigator.userAgent)) {
        os_type_code ="i";
    }
    if(isApp() && ssoId != ""){
        //call android/ios
        if(os_type_code == "a"){
        	android.actionWithAccountManager("2", "${userId}", amValue);
    	}else if(os_type_code == "i"){
    		var obj = {
    		'command': 'actionWithAccountManager',
            'actionType': '2',
            'userId': '${userId}',
            'value': amValue,
            'callback': 'AMreturn'
            };
            var jsonString= JSON.stringify(obj);
            webkit.messageHandlers.callbackHandler.postMessage(jsonString);
    	}
        //console.log(os_type_code+":"+ssoId);
    }

    var AMreturn = function(value) {
        //alert(value);
    }

    var finish = function () {
        window.location.href = "${state}";
    };

    //if additionalUris exist, wait until all additionalUris done.
    if (viewParams.additionalUris.length > 0) {
        var count = 0;
        var maxCount = viewParams.additionalUris.length;
        var maxTimeOut = viewParams.additionalLoginTimeout;
        var startTime = new Date().getTime();

        window.onload = function () {
            for (var i = 0; i < viewParams.additionalUris.length; i++) {
                var additionalUri = viewParams.additionalUris[i];
                if(additionalUri != ""){
                    console.log(additionalUri + ' is called');

                    var iframe = document.createElement('iframe');
                    // iframe.setAttribute('sandbox', 'true');
                    iframe.style.display = "none";
                    iframe.src = additionalUri;

                    iframe.addEventListener('load', function () {
                        console.log(additionalUri+ ' is loaded');
                        count++;
                    });
                    document.body.appendChild(iframe);
                }
            }
        };

        var waitAllDone = function () {
            var currentTime = new Date().getTime();

            //if all loaded, finish
            if (count == maxCount) {
                finish();
            }
            //if time out, finish.
            else if ((currentTime - startTime) > maxTimeOut) {
                console.log('additional maxTimeOut exceeded');
                finish();
            }
            //else, wait again.
            else {
                setTimeout(function () {
                    waitAllDone();
                }, 100)
            }
        }
        waitAllDone();
    }
    //of not, finish logout
    else {
        finish();
    }
</script>
</body>
</html>
