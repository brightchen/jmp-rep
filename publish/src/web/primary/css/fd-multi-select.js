/*

        Alternative Multi Select Widgit by frequency decoder (http://www.frequency-decoder.com/)
        Derived from an original idea by Andy Edmonds at Uzilla (http://uzilla.net/uzilla/my/widgets/multiselect/)

        ChangeLog
        ---------

        10/03/2006 : creation.
        13/03/2006 : only select lists with a classname of "fd-multi-select" now converted.
                     changed the classname "fd-view-sequential" to the more relevant "fd-auto-collapse"
        15/03/2006 : changed the code to append the buttons above the checkboxs for accessibility purposes
        07/04/2006 : added the "fd-add-square-brackets" option
*/


String.prototype.trim = function() { return this.replace(/^\s+|\s+$/, ''); };

var fdMultiSelect;

(function() {

function fdNewMultiSelect(selectList) {
        this.select = selectList;
        this.selectId = selectList.id;

        this.optGroupLabels = new Array();
        this.viewSequential = selectList.className.search("fd-auto-collapse") != -1;

        var o = this;

        o.createNewSelectOptions = function() {
                var wrapperType = o.select.className && o.select.className.search(/fd-wrapper-([u|o])l/) != -1 ? o.select.className.match(/fd-wrapper-([u|o])l/)[0].replace('fd-wrapper-', '') : 'div';
                var brackets    = o.select.multiple && o.select.className && o.select.className.search(/fd-add-square-brackets/) != -1 ? "[]" : "";
                var optGroups   = o.select.getElementsByTagName("optgroup");

                for(var i = 0, optGroup; optGroup = optGroups[i]; i++) {

                        o.optGroupLabels[o.optGroupLabels.length] = optGroup.label.toLowerCase();

                        var wrapperElem = document.createElement(wrapperType);
                        wrapperElem.className = "fd-multi-select-wrapper";
                        if(i % 2 == 0) wrapperElem.className += " fd-wrapper-alt";
                        wrapperElem.id = "fd-multi-select-optgroup-" + o.selectId + "-" + i;

                        var titleElem = document.createElement("div");
                        titleElem.className = "fd-multi-select-title";
                        titleElem.id = "fd-multi-select-title-" + o.selectId + "-" + i;
                        titleElem.appendChild(document.createTextNode(optGroup.label + String.fromCharCode(160)));

                        var span = document.createElement("span");
                        span.appendChild(document.createTextNode("Please click Edit Selections to specify."));

                        titleElem.appendChild(span);

                        var optGroupButton = document.createElement("button");
                        optGroupButton.className = "fd-multi-select-button";
                        optGroupButton.id = "fd-multi-select-button-" + o.selectId + "-" + i;
                        optGroupButton.appendChild(document.createTextNode("Edit Selections"));
                        optGroupButton.onclick = o.expandCollapse;

                        o.select.parentNode.insertBefore(titleElem, o.select);
                        o.select.parentNode.insertBefore(optGroupButton, o.select);
                        o.select.parentNode.insertBefore(wrapperElem, o.select);

                        var options = optGroup.getElementsByTagName("option");

                        var checkBox, label;

                        // force a sequential view for radio buttons
                        if(!o.select.multiple && o.select.className.search(/fd-auto-collapse/) == -1) {
                                o.viewSequential = true;
                        }

                        for(var j = 0, option; option = options[j]; j++) {

                                var elemT = "input";

                                // Internet Explorer hackery (See http://channel9.msdn.com/wiki/default.aspx/Channel9.InternetExplorerProgrammingBugs)
                                /*@cc_on@*/
                                /*@if (@_jscript_version <= 5.6)
                                if(!o.select.multiple) elemT = "<input type='radio' name='"+o.select.name+"' value='"+option.value+"'>";
                                /*@end@*/

                                checkBox         = document.createElement(elemT);

                                // Internet Explorer will throw an error if the element is a radio button
                                try {
                                        checkBox.type = o.select.multiple ? "checkbox" : "radio";
                                } catch (e) {

                                }
                                checkBox.checked = Boolean(option.selected);
                                checkBox.name    = o.select.name + brackets;
                                checkBox.value   = option.value;
                                checkBox.id      = o.select.name + "-" + o.selectId + "-" + i + "-" + j;

                                label = document.createElement("label");

                                label.appendChild(document.createTextNode(String.fromCharCode(160) + option.firstChild.nodeValue));
                                label.htmlFor = o.select.name + "-" + o.selectId + "-" + i  + "-" + j;

                                var tmp;

                                if(wrapperType != "div") {
                                        tmp = document.createElement("li");
                                } else {
                                        tmp = document.createElement("div");
                                }

                                tmp.appendChild(checkBox);
                                tmp.appendChild(label);
                                wrapperElem.appendChild(tmp);
                        }

                        o.collapseGroup(i);
                }
        };

        o.removeSelect = function() {
                // Remove the associated selectlist label
                if(o.select.id) {
                        var labels = document.getElementsByTagName("label");
                        for(var i = 0, label; label = labels[i]; i++) {
                                // IE requires the htmlFor test
                                if(label.htmlFor && label.htmlFor == o.select.id) {
                                        label.parentNode.removeChild(label);
                                        break;
                                } else if(label.getAttribute("for") == o.select.id) {
                                        label.parentNode.removeChild(label);
                                        break;
                                }
                        }
                }

                // Remove the selectlist
                o.select.parentNode.removeChild(o.select);
                o.select = null;
        }

        o.collapseGroup = function(which) {
                var wrapper = document.getElementById("fd-multi-select-optgroup-" + o.selectId + "-" + which);
                var title   = document.getElementById("fd-multi-select-title-" + o.selectId + "-" + which);
                var button  = document.getElementById("fd-multi-select-button-" + o.selectId + "-" + which);
                var span    = title.getElementsByTagName("span")[0];

                while(span.firstChild) span.removeChild(span.firstChild);

                var checkBoxs = wrapper.getElementsByTagName("input");

                var txt = ": ";

                for(var i = 0, checkBox; checkBox = checkBoxs[i]; i++) {
                        if(checkBox.checked) {
                                var label = checkBox.parentNode.getElementsByTagName("label")[0];
                                var val = label.firstChild.nodeValue;
                                val = val.toString().trim();
                                txt += val + "; ";
                        }
                }

                // Remove the trailing semi-colon
                if(txt.length > 2) txt = txt.substring(0, txt.length - 2);

                span.appendChild(document.createTextNode(txt == ": " ? ": Please click Edit Selections to specify." : txt));

                wrapper.style.display = "none";
                button.removeChild(button.firstChild);
                button.appendChild(document.createTextNode("Edit Selections"));

                span.style.display = "inline";
        };

        o.expandGroup = function(which) {
                var wrapper = document.getElementById("fd-multi-select-optgroup-" + o.selectId + "-" + which);
                var title   = document.getElementById("fd-multi-select-title-" + o.selectId + "-" + which);
                var button  = document.getElementById("fd-multi-select-button-" + o.selectId + "-" + which);
                var span    = title.getElementsByTagName("span")[0];

                span.style.display = "none";

                wrapper.style.display = "block";
                button.removeChild(button.firstChild);
                button.appendChild(document.createTextNode("Close"));
        };

        o.expandCollapse = function() {
                var label = this.id.replace("fd-multi-select-button-" + o.selectId + "-", "");
                var id    = "fd-multi-select-optgroup-" + o.selectId + "-" + label;

                if(o.viewSequential) {
                        for(var i = 0; i < o.optGroupLabels.length; i++) {
                                if(i != label) {
                                        o.collapseGroup(i);
                                }
                        }
                }

                if(document.getElementById(id).style.display == "block") o.collapseGroup(label);
                else o.expandGroup(label);

                return false;
        };

        o.createNewSelectOptions();
};

fdMultiSelect = {
        newSelects: [],

        init: function(e) {
                var selects = document.getElementsByTagName("select");
                var optGroups;

                for(var i = 0, select; select = selects[i]; i++) {
                        if(select.disabled || !select.id || select.id == "" || select.className.search("fd-multi-select") == -1) continue;
                        optGroups = select.getElementsByTagName("optgroup");
                        if(optGroups.length > 0) {
                                fdMultiSelect.newSelects[fdMultiSelect.newSelects.length] = new fdNewMultiSelect(select);
                        };
                };

                for(var i = 0, obj; obj = fdMultiSelect.newSelects[i]; i++) {
                        obj.removeSelect();
                }
        }
};

})();

window.onload = fdMultiSelect.init;

