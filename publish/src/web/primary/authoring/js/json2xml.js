
function json2xml(o, tab)
{
	var t = '<iseePageLayout><page section="1">';

	for(var i = 0; i < o.iseepagelayout.page.contentgroup.length; i++)
	{	
		var cg = o.iseepagelayout.page.contentgroup[i];
		// If data row was deleted then skip
		if (cg == null) continue;

		if (cg.type == "article")
			t += '<contentGroup type="article" titleString="' + cg.titlestring + '"><text area="' + cg.text.area + '" newParagraph="true"></text>';
		else if (cg.type == "ad")
			t += '<contentGroup type="ad" titleString="' + cg.titlestring + '"><figure area="' + cg.figure.area + '" captionText="' + cg.titlestring + '"></figure>';

		var email = "";
		if(typeof(cg.email) == "string" && cg.email != undefined) email = cg.email;
		var phone = "";
		if(typeof(cg.phone) == "string" && cg.phone != undefined) phone = cg.phone;
		var sms = "";
		if(typeof(cg.sms) == "string" && cg.sms != undefined) sms = cg.sms;

		t += '<email>' + email + '</email>';
		t += '<phone>' + phone + '</phone>';
		t += '<sms>' + sms + '</sms>';
		t += '</contentGroup>';
	}

	t += '</page></iseePageLayout>';
	return t;
}
