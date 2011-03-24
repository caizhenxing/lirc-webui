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
	<xsl:param name="current.url" select="'http://localhost:8080/LircServer'" />
	<xsl:param name="uuid" select="'none'" />
	<xsl:variable name="v_rootNode" select="." />
	<xsl:variable name="v_std-td-class" select="'td-pad'" />
	<xsl:variable name="v_std-text-class" select="'StdLabel'" />

	<xsl:template match="/">
		<xsl:call-template name="getUUIDElem" />
	</xsl:template>

	<xsl:template name="getUUIDElem">


		<xsl:choose>
			<xsl:when test="$uuid= 'none'">
				<!-- <xsl:message> No UUID Set </xsl:message> -->
				<xsl:call-template name="process">
					<xsl:with-param name="p_uuid_Elem" select="$v_rootNode/remotes" />
				</xsl:call-template>

			</xsl:when>
			<xsl:otherwise>
				<!-- <xsl:message> UUID = <xsl:value-of select="$uuid" /> </xsl:message> -->
				<xsl:call-template name="process">
					<xsl:with-param name="p_uuid_Elem" select="//*[@uuid=$uuid][1]" />
				</xsl:call-template>
			</xsl:otherwise>

		</xsl:choose>

	</xsl:template>

	<xsl:template name="process">
		<xsl:param name="p_uuid_Elem" />

		<xsl:variable name="v_name" select="local-name($p_uuid_Elem)" />
		<!-- <xsl:message> v_name = <xsl:value-of select="$v_name" /> </xsl:message> -->
		<html>
			<head>
						<xsl:call-template name="SetTitle">
							<xsl:with-param name="p_uuid_Elem" select="$p_uuid_Elem" />
						</xsl:call-template>
				
				<style type="text/css" media="all">
					&css;
				</style>
			</head>
			<body>
				<script language="javascript" type="text/javascript" src="./jscripts/lircServer.js"></script>
				<xsl:choose>
					<xsl:when test="$v_name = 'remotes'">
						<xsl:call-template name="Remotes">
							<xsl:with-param name="p_uuid_Elem" select="$p_uuid_Elem" />
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="$v_name = 'remote'">
						<xsl:call-template name="Remote">
							<xsl:with-param name="p_uuid_Elem" select="$p_uuid_Elem" />
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="$v_name = 'group'">
						<xsl:call-template name="Group">
							<xsl:with-param name="p_uuid_Elem" select="$p_uuid_Elem" />
						</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
						<xsl:message>
							Shouldn't get Here v_name =
							<xsl:value-of select="$v_name" />
						</xsl:message>
					</xsl:otherwise>
				</xsl:choose>

				<!-- <xsl:call-template name="AddForm"/> -->
			</body>
		</html>


	</xsl:template>
	
	<xsl:template name="SetTitle">
		<xsl:param name="p_uuid_Elem" />
		<xsl:variable name="v_name" select="local-name($p_uuid_Elem)" />
		
			<xsl:choose>
					<xsl:when test="$v_name = 'remotes'">
					<title>Please choose a Remote</title>
					</xsl:when>
					<xsl:when test="$v_name = 'remote'">
						<title>Current Remote: <xsl:value-of select="$p_uuid_Elem/@name" /></title>
					</xsl:when>
					<xsl:when test="$v_name = 'group'">
						<title>Current Group: <xsl:value-of select="$p_uuid_Elem/@name" /></title>
					</xsl:when>
			</xsl:choose>		
		
		
	</xsl:template>	

	<xsl:template name="Remotes">
		<xsl:param name="p_uuid_Elem" />

		<a href="settings.jsp">Settings</a>

		<table class="stdTable">
			<tbody>
				<xsl:call-template name="TRows">
					<xsl:with-param name="p_uuid_Elem" select="$p_uuid_Elem" />
					<xsl:with-param name="p_href_pre" select="'main.jsp?uuid='" />

				</xsl:call-template>
			</tbody>
		</table>

	</xsl:template>

	<xsl:template name="Remote">
		<xsl:param name="p_uuid_Elem" />
		<a href="main.jsp" id="Btr">Back to remotes</a>
		<table class="stdTable">
			<tbody>
				<xsl:call-template name="TRows">
					<xsl:with-param name="p_uuid_Elem" select="$p_uuid_Elem" />
					<xsl:with-param name="p_href_pre" select="'main.jsp?uuid='" />
				</xsl:call-template>
			</tbody>
		</table>


		<table class="stdTable">
			<tbody>
				<tr>
					<td>
						<xsl:for-each select="$p_uuid_Elem/group">
							<span class="docTitle">
								<!-- <xsl:element name="a" namespace="">
									<xsl:attribute name="href">
										<xsl:value-of select="'#Btr'" />
									</xsl:attribute>
									<xsl:attribute name="id">
										<xsl:value-of select="./@uuid" />
									</xsl:attribute>
									<xsl:value-of select="concat('Group: ',./@name)" />
								</xsl:element> -->
								
										<xsl:call-template name="AddLink">
					<xsl:with-param name="p_uuid_Elem" select="." />
					<xsl:with-param name="p_href_pre" select="'main.jsp?uuid='" />
		</xsl:call-template> 
		


							</span>
							<table class="stdTable">
								<tbody>
									<xsl:call-template name="TRows">
										<xsl:with-param name="p_uuid_Elem" select="." />
									</xsl:call-template>
								</tbody>
							</table>

						</xsl:for-each>
					</td>
				</tr>
			</tbody>
		</table>

	</xsl:template>


	<xsl:template name="Group">
		<xsl:param name="p_uuid_Elem" />
		
		<span class="docTitle">
		Back To Remote:
		<xsl:call-template name="AddLink">
					<xsl:with-param name="p_uuid_Elem" select="$p_uuid_Elem/.." />
					<xsl:with-param name="p_href_pre" select="'main.jsp?uuid='" />
					<xsl:with-param name="p_text_class" select="'docTitle'" />
		</xsl:call-template> 
		
		</span>
		<table class="stdTable">
		<tbody>
		<xsl:call-template name="TRows">
			<xsl:with-param name="p_uuid_Elem" select="$p_uuid_Elem" />
		</xsl:call-template>
		</tbody>
		</table>

	</xsl:template>


	<xsl:template name="TRows">
		<xsl:param name="p_uuid_Elem" />
		<xsl:param name="p_href_pre" />
		<xsl:param name="p_td_class" />
		<xsl:param name="p_text_class" />

		<!-- <xsl:variable name="cols" select="$p_uuid_Elem/@cols" /> -->

		<xsl:variable name="cols">
			<xsl:choose>
				<xsl:when test="$p_uuid_Elem/@cols">
					<xsl:value-of select="$p_uuid_Elem/@cols" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="'4'" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>



		<xsl:variable name="v_td-class">
			<xsl:choose>
				<xsl:when test="$p_uuid_Elem/@tdclass">
					<xsl:value-of select="concat($v_std-td-class,' ', $p_uuid_Elem/@tdclass)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$v_std-td-class" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:variable name="v_text-class">
			<xsl:choose>
				<xsl:when test="$p_uuid_Elem/@textclass">
					<!--  <xsl:value-of select="concat($v_std-text-class,' ', $p_uuid_Elem/@textclass)" /> -->
					<xsl:value-of select="$p_uuid_Elem/@textclass" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$v_std-text-class" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<!-- <xsl:message> p_uuid_Elem =<xsl:value-of select="$p_uuid_Elem/@class" 
			/> v_td-class =<xsl:value-of select="$v_td-class" /> </xsl:message> -->

		<!-- <xsl:message> cols = <xsl:value-of select="$cols" /> </xsl:message> -->

		<xsl:for-each select="$p_uuid_Elem/*[position() mod $cols = 1]">
			<tr>
				<xsl:for-each select=".|following-sibling::*[position() &lt; $cols]">


					<xsl:element name="td" namespace="">
						<xsl:attribute name="class">
							<!--  <xsl:value-of select="$v_td-class" /> -->

							<xsl:choose>
								<xsl:when test="./@tdclass">
									<xsl:value-of select="concat($v_td-class,' ',./@tdclass)" />
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="$v_td-class" />
								</xsl:otherwise>
							</xsl:choose> 


						</xsl:attribute>
						
						<xsl:call-template name="AddLink">
							<xsl:with-param name="p_uuid_Elem" select="." />
							<xsl:with-param name="p_text_class" select="$v_text-class" />
							<xsl:with-param name="p_href_pre" select="$p_href_pre" />
						</xsl:call-template>


					</xsl:element>
				</xsl:for-each>
			</tr>
		</xsl:for-each>
	</xsl:template>
	
		<xsl:template name="AddLink">
		<xsl:param name="p_uuid_Elem" />
		<xsl:param name="p_text_class" />
		<xsl:param name="p_href_pre" />
		
		
								<xsl:element name="a" namespace="">
							<xsl:attribute name="class">
								<xsl:choose>
									<xsl:when test="$p_uuid_Elem/@textclass">
										<xsl:value-of select="$p_uuid_Elem/@textclass" />
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="$p_text_class" />
									</xsl:otherwise>
								</xsl:choose> 

							</xsl:attribute>
							<xsl:attribute name="id">
								<xsl:value-of select="$p_uuid_Elem/@uuid" />
							</xsl:attribute>

							<xsl:choose>
								<xsl:when test="$p_href_pre">
									<xsl:attribute name="href">
										<xsl:value-of select="concat($p_href_pre,$p_uuid_Elem/@uuid)" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<!-- Assume JS/XMLHTTP -->
									<xsl:attribute name="href">
										<xsl:value-of select="''" />
									</xsl:attribute>
									<xsl:attribute name="onClick">
										<!--  <xsl:value-of select="concat('makePOSTRequest(xmlhttp.jsp,uuid=',$p_uuid_Elem/@uuid,');return false')" />-->
										<xsl:value-of select="'grabID(event); return false;'" />
									</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:choose>
								<xsl:when test="$p_uuid_Elem/@label">
									<xsl:value-of select="$p_uuid_Elem/@label" />
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="$p_uuid_Elem/@name" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
		
		
		</xsl:template>

</xsl:stylesheet>
