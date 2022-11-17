/**
 *  This is a modification of the upload.js file which relies on specific element IDs.
 *  Because of this, two upload sections using upload.js cannot be used under the same page.
 *  
 *  The dynamicUploader.js relies on element IDs concatinated with a unique string preferably, 
 *  the file type to be uploaded.
 *  
 *  This modification was made so that pages could have more than one separate javascript upload 
 *  function for whatever type without having any conflict on element IDs which are 
 *  being used when adding/removing/accessing javascript objects.
 *  
 *  USAGE:
 *  Declare ${dynamicType} value as the unique string to be appended on each constant element IDs. 
 *  
 *  Certain constants must be used as element IDs (See CONSTANTS) when creating the upload section.
 *  Please Use the following as ID prefix for adding attachment:
 *  dropcap_
 *  attachment_
 *  attachments_
 *  addupload_
 *  attachmentmarker_
 *  
 *  SAMPLE USE:
 *  <div id="attachment_${dynamicType}" style="display: none;"> 
 *  	<div id="dropcap_${dynamicType}">1</div>
 *  	
 *  	<input id="file" name="upload" type="file"/><br/>
 *  	<a href="#" onclick="javascript:dynamicRemoveFile(this.parentNode.parentNode, this.parentNode, '${dynamicType}');">Remove</a>
 *  </div>
 *  
 *  <div id="attachments_${dynamicType}"><br/>
 *  	<a id="addupload_${dynamicType}" href="javascript:addDynamicUpload(3, 'alt', 'upload', '${dynamicType}')">Attach another file</a><br/><br/>
 *  	<span id="attachmentmarker_${dynamicType}"></span>
 *  </div>
 *  
 *  //Add Upload Button Here
 *  
 */

var maximum = 0; // maximum # of attachments allowed
var currentDynamicUploads = 0; // current # of attachment sections on the web page
var nameDynamicDesc = ''; // Name property for the Description Input field
var nameDynamicFile = ''; // Name property for the File Input field
var inputTypeID = ''; // Name property for the File Input field
var scrollPosVert = 0; // stores the current scroll position on the form

//CONSTANTS
var separatorChar = '_';
var ADD_UPLOAD = 'addupload' + separatorChar;
var ATTACHMENT = 'attachment' + separatorChar;
var ATTACHMENTS = 'attachments' + separatorChar;
var DROP_CAP = 'dropcap' + separatorChar;
var ATTACHMENT_MARKER = 'attachmentmarker' + separatorChar;
var ATTACH_MSG = 'Attach another ';


// for some reason when a div is taken out, the form
// will scroll to the top on both Firefox and IE

// SCROLL FUNCTIONS 
function saveScrollPos(offset)
{
	scrollPosVert=(document.all)?document.body.scrollTop:window.pageYOffset-offset;
}

function setScrollPos()
{
	window.scrollTo(0, scrollPosVert);
	setTimeout('window.scrollTo(0, scrollPosVert)',1);
}


/** This function adds a new attachment section to the form
  * It is called when the user clicks the "Attach a file" button...
  * It takes three arguments:
  * maximumUploads - the maximum number of attachments allowed
  * descFieldName - the field name for the Description Input field
  * fileFieldName - the field name for the File Input field
  * uniqueFileType - this will be appended in all unique IDs; must be any uniqueName you appended on all uniform IDs
  */
function addDynamicUpload(maximumUploads, descFieldName, fileFieldName, uniqueFileType) {
	nameDynamicDesc=descFieldName;
	nameDynamicFile=fileFieldName;
	maximum = Number(maximumUploads);

	currentDynamicUploads++;
	if (currentDynamicUploads>maximum){
		return;
	} 

	var addUploadID = ADD_UPLOAD + uniqueFileType; 
	if (currentDynamicUploads>0){
		document.getElementById(addUploadID).childNodes[0].data=(ATTACH_MSG + uniqueFileType);
	}

	if (currentDynamicUploads==maximum){
		document.getElementById(addUploadID).style.visibility='hidden';
	}

	var rootAttachmentFormID = ATTACHMENT + uniqueFileType;
	// First, clone the hidden attachment section
	var newFields = document.getElementById(rootAttachmentFormID).cloneNode(true);

	//Attach a file
	newFields.id = '';
	
	// Make the new attachments section visible
	newFields.style.display = 'block';


	// loop through tags in the new Attachment section
	// and set ID and NAME properties
	//
	// NOTE: the control names for the Description Input
	// field and the file input field are created
	// by appending the currentDynamicUploads variable
	// value to the nameDynamicFile and nameDynamicDesc values
	// respectively
	//
	// In terms of Xaraya, this means you'll need to name your
	// DD properties will need names like the following:
	// "AttachmentDesc1"
	// "AttachmentFile1"
	// "AttachmentDesc2"
	// "AttachmentFile2"
	// "AttachmentDesc3"
	// "AttachmentFile3"
	// et cetera...

	var newField = newFields.childNodes;
	var dropCapID = DROP_CAP + uniqueFileType;
	for (var i=0;i<newField.length;i++) {
		if (newField[i].name==nameDynamicFile) {
			newField[i].id=nameDynamicFile+currentDynamicUploads;
			newField[i].name=nameDynamicFile;
			newField[i].setAttribute('required','');
		}

		if (newField[i].name==nameDynamicDesc) {
			newField[i].id=nameDynamicDesc+currentDynamicUploads;
			newField[i].name=nameDynamicDesc
		}
		
		if (newField[i].id==dropCapID) {
			newField[i].id=dropCapID+currentDynamicUploads;
			newField[i].childNodes[0].data=currentDynamicUploads;
		}
	}


	// Insert our new Attachment section into the Attachments Div
	// on the form...
	var attachmentMakerID = ATTACHMENT_MARKER + uniqueFileType;  
	var insertHere = document.getElementById(attachmentMakerID); 
	insertHere.parentNode.insertBefore(newFields,insertHere);
}


// This function removes an attachment from the form
// and updates the ID and Name properties of all other
// Attachment sections
function dynamicRemoveFile(container, item, uniqueFileType) {
	if (currentDynamicUploads<=1){
		//return;
	}
	
	// get the ID number of the upload section to remove
	var tmp = item.getElementsByTagName('input')[0];
	var basefieldname = '';
	if (tmp.type=='text') basefieldname = nameDynamicDesc; else basefieldname = nameDynamicFile;
	
	
	var iRemove=Number(tmp.id.substring(basefieldname.length, tmp.id.length));
	
	// Shift all INPUT field IDs and NAMEs down by one (for fields with a
	// higher ID than the one being removed)
	var attachmentsID = ATTACHMENTS + uniqueFileType;
	var dropcapID = DROP_CAP + uniqueFileType;
	var addUploadID = ADD_UPLOAD + uniqueFileType;

	var x = document.getElementById(attachmentsID).getElementsByTagName('input');
	for (i=0;i<x.length;i++) {
		if (x[i].type=='text') basefieldname=nameDynamicDesc; else basefieldname=nameDynamicFile;
		
		var iEdit = Number(x[i].id.substring(basefieldname.length, x[i].id.length));
		if (iEdit>iRemove) {
			x[i].id=basefieldname+(iEdit-1);
			x[i].name=basefieldname+(iEdit-1);
		}
	}

	// Run through all the DropCap divs (the number to the right of the attachment
	// section) and update that number...
	x=document.getElementById(attachmentsID).getElementsByTagName('div');
	for (i=0;i<x.length;i++) {
		// Verify this is actually the "dropcap" div
		if (x[i].id.substring(0, String(dropcapID).length)==dropcapID) {
			ID = Number(x[i].id.substring(String(dropcapID).length, x[i].id.length));
			
			// check to see if current attachment had a higher ID than the one we're
			// removing (and thus needs to have its ID dropped)
			if (ID>iRemove) {
				x[i].id=dropcapID+(ID-1);
				x[i].childNodes[0].data=(ID-1);
			}
		}
	}

	currentDynamicUploads--;
	saveScrollPos(0);
	container.removeChild(item);
	setScrollPos();
	
	document.getElementById(addUploadID).style.visibility='visible';
	
	if (currentDynamicUploads==0){
		document.getElementById(addUploadID).childNodes[0].data = ATTACH_MSG + uniqueFileType;
	}
}
