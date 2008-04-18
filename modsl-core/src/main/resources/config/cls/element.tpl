<%
	header_baseline = (int)(element.position.y + config.elementHeaderFT.getExtBaseline(0));
	header_line = (int)(element.position.y  + config.elementHeaderFT.getExtHeight(1));
	vm_line = header_line + config.elementDetailFT.getExtHeight(element.attributes.size());
%>	

<rect name="$element.name" x="${(int)element.position.x}" y="${(int)element.position.y}" 
    width="${(int)element.size.x}" height="${(int)element.size.y}" rx="$config.elementRx" class="element"/>

<line x1="${(int)element.position.x}" y1="$header_line" 
    x2="${(int)(element.position.x + element.size.x)}" y2="$header_line" class="element"/>

<%	if (element.attributes.size() > 0 && element.methods.size() > 0) { %>
	<line x1="${(int)element.position.x}" y1="$vm_line" 
	    x2="${(int)(element.position.x + element.size.x)}" y2="$vm_line" class="element"/>
<%	} %>

<text x="${(int)(element.position.x + config.elementHeaderFT.leftPadding)}" 
    y="$header_baseline" font-size="$config.elementHeaderFT.fontSize" class="element_header">$element.name</text>