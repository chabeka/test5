package fr.urssaf.image.commons.maquette.tool;


/**
 * @author 	CER6990172
 * @desc	cette classe gère les boites utilisées par la section <leftcol> de la maquette
 * 			
 */
public final class InfoBoxItem 
{	
	private String shortIdentifier ;
	private String title;
	private String boxDesc;
	private String content;
	
	public InfoBoxItem() {
		shortIdentifier = "" ;
		title = "" ;
		boxDesc = "" ;
		content = "" ;
	}
	
	public InfoBoxItem(String shortIdentifier, String title, String boxDesc) {
		this.shortIdentifier = shortIdentifier ;
		this.title = title;
		this.boxDesc = boxDesc;
		content = "" ;
	}

	public String getShortIdentifier() {
		return shortIdentifier;
	}
	
	public void setShortIdentifier(String shortIdentifier) {
		this.shortIdentifier = shortIdentifier;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBoxDesc() {
		return boxDesc;
	}
	
	public void setBoxDesc(String boxDesc) {
		this.boxDesc = boxDesc;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void addSpan( String attId, String attTitle, String tagContent )
	{
		content += "<span id=\"" + shortIdentifier + "-" + attId + "\" title=\"" + attTitle + "\">" 
				+ tagContent + "</span><br />\n" ;
	}
	
	public void addBtn( String attId, String name, String javascript )
	{
		content += "<input id=\"" + shortIdentifier + "-" + attId + "\" " +
				"class=\"" + shortIdentifier + "-" + attId + "\" type=\"button\" value=\"" 
				+ name + "\" onclick=\"" + javascript + "\" tabindex=\"0\" />" ;
	}
}
