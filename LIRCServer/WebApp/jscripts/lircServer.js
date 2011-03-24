var win = null;


var OK_bool = false;
var OKB = true;
// AJax Methods
// var http_request = false;

var theId
function grabID(event){ 
if (window.event)
{
// code for ie
theID = window.event.srcElement.id
} 
else
{
// code for firefox
theID = event.target.id
}
// alert(theID);
setUUID(theID);
}

function setUUID(uuid){
	var param = "uuid="+uuid;
	// alert("param = "+param);
	makePOSTRequest('xmlhttp.jsp', param);
}

function createNewXMLFile(){
	document.SettingsForm.createXML.value = 'yes';
	document.SettingsForm.submit();
	alert("Trying to create a new XML File using: \n Dir = "+document.SettingsForm.xmlDir.value +"\n Filename = "+document.SettingsForm.xmlFile.value);
}

function resetXMLUUID(){
	document.SettingsForm.resetXML.value = 'yes';
	document.SettingsForm.submit();
	alert("Resetting XML UUID");
}

function message(){
	alert("message called");
}

function alertMe(message){
	alert("alertMe called message = "+message);
}
function makePOSTRequest(url, parameters) {
   var http_request = false;
   // alert("makePOSTRequest parameters= "+parameters +" url = "+url);
   OK_bool = false;
   // alert("makePOSTRequest OK_bool4= "+OK_Bool_S);
   if (window.XMLHttpRequest) {
      // Mozilla, Safari,...
      http_request = new XMLHttpRequest();
      if (http_request.overrideMimeType) {
         // set type accordingly to anticipated content type
         // http_request.overrideMimeType('text/xml');
         http_request.overrideMimeType('text/html');
         }
      }
   else if (window.ActiveXObject) {
      // IE
      try {
         http_request = new ActiveXObject("Msxml2.XMLHTTP");
         }
      catch (e) {
         try {
            http_request = new ActiveXObject("Microsoft.XMLHTTP");
            }
         catch (e) {
            }
         }
      }
   if (!http_request) {
      alert('Cannot create XMLHTTP instance');
	  OK_bool = false;
      return OK_bool;
      }
	
	try{
   http_request.onreadystatechange = function() {
   if (http_request.readyState == 4 && http_request.status == 200){
      OK_bool = alertContents(http_request);
	 // alert("makePOSTRequest OK_bool1= "+OK_bool);
		// return OK_bool;
	  }
      }
   http_request.open('POST', url, true);
   http_request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
   http_request.setRequestHeader("Content-length", parameters.length);
   http_request.setRequestHeader("Connection", "close");
   http_request.send(parameters);
   }
   catch(e){ alert("Exception thrown in Ajax update e = "+e);
   }

   // alert("makePOSTRequest OK = "+OK);
   // alert('alertContents http_request.readyState ='+http_request.readyState
	// +' http_request.status ='+http_request.status);
   // alert("makePOSTRequest OK_bool2= "+OK_bool);
   return OKB;
   }
   
function alertContents(http_request) {
var OKText = "NOTOK";
OKB = false;
   // alert('alertContents http_request.readyState ='+http_request.readyState
	// +' http_request.status ='+http_request.status);
   if (http_request.readyState == 4) {
      if (http_request.status == 200) {
        // alert(http_request.responseText);
         OKText = http_request.responseText;
         if(OKText == "OK") {
            OKB = true;
            }
         if(OKText != "OK") {
            OKB = false;
            }
         if(OKB) {
           // postAjaxUpdatePage(OKB);
            }
         // return "OK";
         // alert('alertContents OK = '+OKB + "OKText = "+OKText);
         // result = http_request.responseText;
         // document.getElementById('myspan').innerHTML = result;
         }
      else {
         alert('There was a problem with the request.');
         }
      }
	  return OKB;
   }
function SendTextContent(Content, guid, xmlfile,SessionID) {
   var guidAtt = 'guid=\"' + guid + '\"';
  // var retlinkAtt = ' retlink=\"' + retlink + '\"';
   var xmlfileAtt = ' xmlfile=\"' + xmlfile + '\"';
   var st1 = '<?xml version=\"1.0\"?><textContent ';
   var st2 = guidAtt + xmlfileAtt + '> ';
   var st3 = '</textContent>';
   var cd1 = '<![CDATA[';
   var cd2 = ' ]]>';
   var xmlstring = st1 + st2 + cd1 + Content + cd2 + st3;
   // alert("XMLString ="+xmlstring);
   var OK_bt = makePOSTRequest('xsltEndPoint;jsessionid='+SessionID, xmlstring);
   // alert('SendTextContent OK_bt ='+OK_bt);
   return OK_bt;
   }

function SendAttribContent(Content, guid, xmlfile, AttName,SessionID) {
   var guidAtt = 'guid=\"' + guid + '\"';
   var xmlfileAtt = ' xmlfile=\"' + xmlfile + '\"';
   var AttAtt = AttName + '=\"' + Content + '\"';
   var st1 = '<?xml version=\"1.0\"?>\<attContent ';
   var st2 = guidAtt + xmlfileAtt + '> ';
   var at1 = '<at ' + AttAtt + '/>';
   var st3 = '</attContent>';
   var xmlstring = st1 + st2 + at1 + st3;
   // alert("XMLString ="+xmlstring);
   var OK_ba = makePOSTRequest('xsltEndPoint;jsessionid='+SessionID, xmlstring);
   // alert('SendTextContent OK ='+OK);
   return OK_ba;
   }
function postAjaxUpdatePage(OKInd) {
   // alert("postAjaxUpdatePage called PageElement = "+PageElement +" DocE =
	// "+DocE.nodeName);
   // Evalute the HTMLElement
   if(PageElement == "TextAreaE") {
      if(OKInd) {
         setTextAreaContent(DocE);
         }
      if(!OKInd) {
         alert("Sorry but this text has not been updated");
         }
      }
   if(PageElement == "SelectE") {
      if(OKInd) {
         // setSelectE();
         }
      if(!OKInd) {
         alert("Sorry but this Select Item has not been updated");
         }
      }
   if(PageElement == "Input4SelectE") {
      if(OKInd) {
         // setInput4SelectE();
         }
      if(!OKInd) {
         alert("Sorry but this Select Item has not been updated");
         }
      }
   }


function openStdPopup(popURL) {
	window
			.open(
					popURL,
					'',
					'toolbar=0,scrollbars=1,location=0,statusbar=1,menubar=0,resizable=1,width=800,height=600,left= 362,top= 234');
}

function clickLink(linkId) {
	var fireOnThis = document.getElementById(linkId);
	// alert("clickLink called linkId = "+linkId + " fireOnThis = "+fireOnThis);
	if (document.createEvent) {
		var evObj = document.createEvent('MouseEvents')
		evObj.initEvent('click', true, false)
		fireOnThis.dispatchEvent(evObj)
	} else if (document.createEventObject) {
		fireOnThis.fireEvent('onclick')
	}
}

function clickLinkByClass(Classname, tagname) {

	var targetE1;
	myObjCol = getElementsByClassName(Classname, tagname);
	if (myObjCol != null && myObjCol.length > 0) {
		for (i = 0, j = myObjCol.length; i < j; i++) {
			targetE1 = myObjCol[i];
		}
		// var targetID1 = targetE1.id;
		if (document.createEvent) {
			var evObj = document.createEvent('MouseEvents')
			evObj.initEvent('click', true, false)
			targetE1.dispatchEvent(evObj)
		} else if (document.createEventObject) {
			targetE1.fireEvent('onclick')
		}
		// alert("clickLinkByClass called");
		// pausecomp(1000);
	}
}

function clickLinkHref(linkId){
	// alert("clinkLinkHref called linkId = "+linkId);
	var alink = document.getElementById(linkId);
	if ( alink != null ) { 
	var ahref = alink.href;
	// alert("clickLinkHref called linkId = "+linkId + " ahref = "+ahref);
	window.location.href = ahref;
	}
	/*
	 * if ( alink == null ) { alert("clickLinkHref called linkId = "+linkId + "
	 * alink NOT found "); } //try by class and tag myObjCol =
	 * getElementsByClassName('retLink', 'a'); if (myObjCol != null &&
	 * myObjCol.length > 0) { alert("clickLinkHref called linkId = "+linkId + "
	 * alink found by class "); } else{
	 * 
	 * alert("clickLinkHref called linkId = "+linkId + " alink NOT found by
	 * class "); }
	 */
	
	
}

function unescapeXML(XMLData) {
	var str = XMLData;
	var gt;
	// alert(str);
	// replace &lt; with <
	str = str.replace(/&lt;/gi, "<");
	// replace &gt; with >
	str = str.replace(/&gt;/gi, ">");
	// replace &quot; with "
	str = str.replace(/&quot;/gi, "\"");
	// replace &apos; with '
	str = str.replace(/&apos;/gi, "'");
	// replace &amp; with &
	str = str.replace(/&amp;/gi, "&");
	// replace <![CDATA[
	str = str.replace(/<!\[CDATA\[/gi, "");
	// replace ]]>
	str = str.replace(/\]\]>/gi, "&");
	// alert(str);
	return str
}
function escapeXML(XMLData) {
	var str = XMLData;
	var gt;
	// replace &lt; with <
	str = str.replace(/</gi, "&lt;");
	// replace &gt; with >
	str = str.replace(/>/gi, "&gt;");
	// replace &quot; with "
	str = str.replace(/\"/gi, "&quot;");
	// replace &apos; with '
	str = str.replace(/\'/gi, "&apos;");
	// replace &amp; with &
	str = str.replace(/&/gi, "&amp;");
	return str
}

function getElementsByClassName(strClass, strTag, objContElm) {
	strTag = strTag || "*";
	objContElm = objContElm || document;
	var objColl = (strTag == '*' && document.all) ? document.all : objContElm
			.getElementsByTagName(strTag);
	var arr = new Array();
	var delim = strClass.indexOf('|') != -1 ? '|' : ' ';
	var arrClass = strClass.split(delim);
	// var msg ="";
	for (i = 0, j = objColl.length; i < j; i++) {
		// msg = msg +" "+ objColl[i].className +" ,";
		var arrObjClass = objColl[i].className.split(' ');
		if (delim == ' ' && arrClass.length > arrObjClass.length)
			continue;
		var c = 0;
		comparisonLoop: for (k = 0, l = arrObjClass.length; k < l; k++) {
			for (m = 0, n = arrClass.length; m < n; m++) {
				if (arrClass[m] == arrObjClass[k])
					c++;
				if ((delim == '|' && c == 1)
						|| (delim == ' ' && c == arrClass.length)) {
					arr.push(objColl[i]);
					break comparisonLoop;
				}
			}
		}
	}
	// alert('List of classes of tag called '+strTag +" = "+msg);
	return arr;
}
function getElementsByClass(searchClass, node, tag) {
	var classElements = new Array();
	if (node == null)
		node = document;
	if (tag == null)
		tag = '*';
	var els = node.getElementsByTagName(tag);
	var elsLen = els.length;
	// var msg ="";
	var pattern = new RegExp('(^|\\s)' + searchClass + '(\\s|$)');
	for (i = 0, j = 0; i < elsLen; i++) {
		// msg = msg +" "+ els[i].className +" ,";
		if (pattern.test(els[i].className)) {
			classElements[j] = els[i];
			j++;
		}
	}
	// alert('List of classes of tag called '+tag +" = "+msg);
	return classElements;
}

function testdelay() {
	alert('button clicked');
	setTimeout("alert('button clicked delay 1250')", 1250);
	pausecomp(2000);
	alert('button clicked pause 2000');
}
function pausecomp(millis) {
	date = new Date();
	var curDate = null;
	do {
		var curDate = new Date();
	} while (curDate - date < millis);
}

function setInput4SelectE() {
	// For use when there is a button updating a select
	// Given Ajax means not refreshing the page & that a page is only
	// refreshed after a save/load event then this is unnecessary. Keep it for
	// now
	alert("setInput4SelectE called SelectVal = " + SelectVal);

}

function setSelectE() {
	// For use when a select updates itself
	// Given Ajax means not refreshing the page & that a page is only
	// refreshed after a save/load event then this is unnecessary. Keep it for
	// now
	alert("setSelectE is not implemented yet SelectVal =" + SelectVal);

}

function callrefreshDom() {
	alert("callrefreshDom1 called NodeChosen  =" + NodeChosen);
	window.location.reload();
	alert("callrefreshDom2 called NodeChosen  =" + NodeChosen);
}

function setmainDivSize() {
	var maindiv = document.getElementById('maindiv');
	var posArr = findPos(maindiv);

	var myHeight = 0;
	if (typeof (window.innerHeight) == 'number') {
		// Non-IE
		myHeight = window.innerHeight;
	} else if (document.documentElement
			&& document.documentElement.clientHeight) {
		;
		myHeight = document.documentElement.clientHeight;
	} else if (document.body && document.body.clientHeight) {
		myHeight = document.body.clientHeight;
	}

	// alert("myHeight = "+myHeight);

	var MainDivH = myHeight - posArr[1] - 20;
	/*
	 * alert("screen height = "+screen.height +" document.body.clientHeight
	 * ="+document.body.clientHeight +" maindiv FromTop = "+posArr[1] +"
	 * MainDivH = "+MainDivH);
	 */
	if (maindiv != null) {
		maindiv.style.height = MainDivH;
	}

}

function SetFilePath() {
	document.form1.XMLFile.value = document.FileChoseform.filechoose.value;
	document.form1.submit();
}

function CreateFile() {
	document.form1.XMLFile.value = document.FileChoseform.filechoose.value;
	document.form1.create.value = 1;
	document.form1.submit();
}

function CloseRefreshParent() {
	window.opener.location.href = window.opener.location.href;
	window.close();

}

function OpenFileLinkUUID(link,attrib) {

		/*
		 * if(link.parentNode != null){ var D1 = link.parentNode; alert("link
		 * parent D1 = "+D1.innerHTML); }
		 */	
var uuid = FindElemContainingUUID(link);
var jSessionID="";
var SessionID = document.editorform.SessionID.value;
// XFormMain.jsf;jsessionid=6F9679C07F8E7E8F5BD0E161CBACBE00
if(SessionID.length > 0){
	jSessionID = ';jsessionid='+SessionID;
}
	// alert("OpenFileLinkUUID uuid = " + uuid +" link = "+link.innerHTML);
	// alert("OpenFileLinkUUID uuid = " + uuid);

// alert("OpenFileLinkUUID uuid = " + uuid +" jSessionID = "+jSessionID);
	
if(attrib.length > 0){
// alert("OpenFileLinkUUID attrib set and = " + attrib);
openStdPopup('./filebrowserJSF.jsf'+jSessionID+'?call-init=true&type=false&uuid='+uuid+'&attrib='+attrib);
}	
if(attrib.length == 0){
openStdPopup('./filebrowserJSF.jsf'+jSessionID+'?call-init=true&type=false&uuid='+uuid);
}
	
	
}

function FindElemContainingUUID(link) {
	// Add Attrib finding?
	var uuid = "";
	// UUID regex
	var rexp = new RegExp(
			"[A-Fa-f0-9]{8}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{12}");
	// Get all the child elements
	var children = getDescendantElements(link, false);
	for ( var i = 0; i < children.length; i++) {
	// alert("children[i].innerHTML = " + children[i].innerHTML);
		if (rexp.test(getText(children[i]))) {
	// alert("children[i].innerHTML OK 4 REGEX = " + children[i].innerHTML);
			uuid = getText(children[i]);
			break;
		}
	}
	return uuid;
}

function getDescendantElements(obj, childrenOnly) {
	var elems = getChildElements(obj);
	var results = [];
	for ( var i = 0; i < elems.length; i++) {
		results.push(elems[i]);
		if (!childrenOnly) {
			results = results.concat(getDescendantElements(elems[i],
					childrenOnly));
		}
	}
	return results;
}

function getChildElements(obj) {
	var children = obj.childNodes;
	var elems = [];
	if (children) {
		for ( var i = 0; i < children.length; i++) {
		// alert("children[i].nodeType = " + children[i].nodeType);
			if (children[i].nodeType == document.ELEMENT_NODE) // it's an
																// element node
			{
				elems.push(children[i]);
			}
		}
	}
	return elems;
}

function getText(n)
{
  if('textContent' in n) {
    return n.textContent;
  } else if('innerText' in n) {
    return n.innerText;
  } else {
    // Call a custom collecting function, throw an error, something like that.
  }
}




