<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
	<!ENTITY css SYSTEM "../css/basic.css">
]>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0"
		omit-xml-declaration="yes" />
	<xsl:strip-space elements="*" />
	<xsl:param name="current.url"/>
	<xsl:variable name="v_rootNode" select="." />

	<xsl:template match="/">
		<xsl:call-template name="process" />
	</xsl:template>

	<xsl:template name="process">


		<html>
			<head>
			<title>Lirc Server Settings</title>
				 <style type="text/css" media="all">
					&css;
				</style>
			</head>
			<body>
				<script language="javascript" type="text/javascript"
					src="./jscripts/lircServer.js"></script>
				<a href="index.jsp">Main</a>	
				<div class="divCenter">
					<span class="docTitle">Lirc Server Settings</span>
				</div>	
				<xsl:call-template name="AddForm"/>
			</body>
		</html>


	</xsl:template>

	<xsl:template name="AddForm">

		<span class="Edit_html">				
					<xsl:element name="form">
					<xsl:attribute name="enctype"><xsl:value-of
						select="'application/x-www-form-urlencoded'" /></xsl:attribute>
					<xsl:attribute name="method"><xsl:value-of
						select="'GET'" /></xsl:attribute>
					<xsl:attribute name="name"><xsl:value-of
						select="'SettingsForm'" /></xsl:attribute>
					<xsl:attribute name="action"><xsl:value-of
						select="$current.url" /></xsl:attribute>	
		
				<table class="stdTable">
					<tbody>
						<xsl:call-template name="AddIRTable" />
						<!--  <xsl:call-template name="AddXMLDirTable"/> -->
						<xsl:call-template name="AddXMLPathTable" />
						<tr>
							<td />
							<td>
								<input class="xfInput" type="submit" value="Update Values" />
							</td>
						</tr>
						<!--  set the OK's to glabal variables -->
							<xsl:if test="//xmlDir/@isOK='true' and //xmlPath/@isOK='false' and //irsendPath/@isOK='true'">
							
							<tr>
							<td />
							<td class="widthCol100">
							<!-- Test if Path OK -->
							<!-- Add Button -->
							<button name="CreateNew" type="button" class="widthCol100"
							onClick="createNewXMLFile();return false;">No Config Found - Create a New Default Config?</button>
							</td>
							</tr>
							</xsl:if>	
							<!--  <xsl:message>xmlPath123 = <xsl:value-of select="//xmlPath/@isOK" /></xsl:message> -->
							<xsl:if test="//xmlPath/@isOK='true'">
							<tr>
							<td />
							<td class="widthCol100">
							<!-- Test if Path OK -->
							<!-- Add Button -->
							<button name="CreateNew" type="button" class="widthCol100"
							onClick="resetXMLUUID();return false;">ResetUUID</button>
							</td>
							</tr>
							
							</xsl:if>
							

					</tbody>
				</table>
				 <input type="hidden" id="createXML" name="createXML" value="no"/>
				 <input type="hidden" id="resetXML" name="resetXML" value="no"/>
			</xsl:element>	
		</span>
	</xsl:template>

	<xsl:template name="AddIRTable">
	
	<xsl:variable name="v_isErr" select="//irsendPath/@isOK"/>
	
		<tr class="HeadingTR">
			<td />
			<td class="Heading">IR Settings </td>
		</tr>
		
		

		<xsl:call-template name="AddFormRow">
			<xsl:with-param name="p_label" select="'irsend'" />
			<xsl:with-param name="p_val" select="//*/@irsend" />
			<xsl:with-param name="p_isErr" select="$v_isErr" />
		</xsl:call-template>
		<xsl:call-template name="AddFormRow">
			<xsl:with-param name="p_label" select="'lircDir'" />
			<xsl:with-param name="p_val" select="//*/@lircDir" />
			<xsl:with-param name="p_isErr" select="$v_isErr" />
		</xsl:call-template>
		<xsl:call-template name="AddDescRow">
			<xsl:with-param name="p_label" select="'irsendPath'" />
			<xsl:with-param name="p_val" select="//*/@irsendPath" />
		</xsl:call-template>

		<xsl:if test="$v_isErr='false'">
			<xsl:call-template name="PrintErrors">
				<xsl:with-param name="p_node" select="//irsendPath[1]" />
			</xsl:call-template>
		</xsl:if>



	</xsl:template>


	<xsl:template name="AddXMLPathTable">
	<xsl:variable name="v_xd_isErr" select="//xmlDir/@isOK"/>
		<tr class="HeadingTR">
			<td />
			<td class="Heading">XML Settings</td>
		</tr>
		<xsl:call-template name="AddFormRow">
			<xsl:with-param name="p_label" select="'xmlDir'" />
			<xsl:with-param name="p_val" select="//*/@xmlDir" />
			<xsl:with-param name="p_isErr" select="$v_xd_isErr" />
		</xsl:call-template>




		<xsl:if test="//xmlDir/@isOK='false'">
			<xsl:call-template name="PrintErrors">
				<xsl:with-param name="p_node" select="//xmlDir[1]" />
			</xsl:call-template>
		</xsl:if>

<xsl:variable name="v_xp_isErr" select="//xmlPath/@isOK"/>
		<xsl:call-template name="AddFormRow">
			<xsl:with-param name="p_label" select="'xmlFile'" />
			<xsl:with-param name="p_val" select="//*/@xmlFile" />
			<xsl:with-param name="p_isErr" select="$v_xp_isErr" />
		</xsl:call-template>
		<xsl:call-template name="AddDescRow">
			<xsl:with-param name="p_label" select="'xmlPath'" />
			<xsl:with-param name="p_val" select="//*/@xmlPath" />
		</xsl:call-template>


		<xsl:if test="//xmlPath/@isOK='false'">
			<xsl:call-template name="PrintErrors">
				<xsl:with-param name="p_node" select="//xmlPath[1]" />
			</xsl:call-template>
		</xsl:if>

	</xsl:template>



	<xsl:template name="AddFormRow">
		<xsl:param name="p_label" />
		<xsl:param name="p_val" />
		<xsl:param name="p_isErr" />
		
		<xsl:variable name="v_class" >
		<xsl:choose>
		<xsl:when test="$p_isErr = 'true'">
		<xsl:value-of select="'xfInput'"/>
		</xsl:when>
		<xsl:otherwise>
		<xsl:value-of select="'xfError'"/>
		</xsl:otherwise>
		</xsl:choose>
		
		</xsl:variable>

		<tr>
			<td class="widthCol10">
				<label class="StdLabel">
					<xsl:value-of select="$p_label" />
				</label>
			</td>
			<td class="widthCol100">
				<xsl:element name="input">
					<xsl:attribute name="value"><xsl:value-of
						select="$p_val" /></xsl:attribute>
					<xsl:attribute name="name"><xsl:value-of
						select="$p_label" /></xsl:attribute>
					<xsl:attribute name="class"><xsl:value-of
						select="$v_class" /></xsl:attribute>
				</xsl:element>
			</td>
		</tr>
	</xsl:template>

	<xsl:template name="AddDescRow">
		<xsl:param name="p_label" />
		<xsl:param name="p_val" />

		<tr>
			<td class="widthCol10">
				<label class="StdLabel">
					<xsl:value-of select="$p_label" />
				</label>
			</td>
			<td  class="desc,widthCol100">
				<xsl:value-of select="$p_val" />
			</td>
		</tr>
	</xsl:template>


	<xsl:template name="PrintErrors">
		<xsl:param name="p_node" />

		<tr>
			<td class="widthCol10">
				<label class="errLabel">
					<xsl:value-of select="'Errors Found'" />
				</label>
			</td>
			<td class="desc,widthCol100">
				<xsl:for-each select="$p_node//error">
					<p class="errTxt">
						<xsl:value-of select="@errorText" />
					</p>
				</xsl:for-each>
			</td>
		</tr>
	</xsl:template>



</xsl:stylesheet>
