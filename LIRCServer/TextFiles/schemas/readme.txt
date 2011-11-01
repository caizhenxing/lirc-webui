The Schemas were generated using XML-Beans 2.5.0 inst2xsd

See :
http://xmlbeans.apache.org/docs/2.0.0/guide/tools.html#inst2xsd

2 design types (vb = venetian blind , ss = salami slice)
2 content types - String & smart.

command for recreating with a ss design & a string content type = :

inst2xsd  -design ss -simple-content-types string  -outPrefix config-ss config-www.xml
