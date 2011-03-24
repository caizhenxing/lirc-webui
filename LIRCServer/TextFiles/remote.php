<?php
if (!isset($_SERVER))
{  $_GET    = &$HTTP_GET_VARS;
   $_POST    = &$HTTP_POST_VARS;
   $_ENV    = &$HTTP_ENV_VARS;
   $_SERVER  = &$HTTP_SERVER_VARS;
   $_COOKIE  = &$HTTP_COOKIE_VARS;
   $_REQUEST = array_merge($_GET, $_POST, $_COOKIE);
}

function irsend($command,$remote,$key)
{ if(!$handle=popen("/usr/bin/irsend $command \"$remote\" \"$key\" 2>&1","r"))
 {echo ("Could not fork irsend");
 }
 $output="";
 while(!feof($handle))
 {$output = $output . fgets($handle, 1024);
 }
 pclose($handle);
 return split("\n",str_replace("/usr/bin/irsend: ","",$output));
}

function keys($remote)
{print($remote. "<br>\n");
 $keys=irsend("list",$remote,"");
 for ($i = 0; $i < count($keys);$i++)
 {list($label,$code,$keyname)=split(" ",$keys[$i]);
  print("<a href=?remote=" . $remote .  "&key=" . $keyname . ">" . $keyname . "<br>\n");
 }
 print("<a href=remote.php>return</a>");
}

if($_GET)
{$remote=$_GET['remote'];
 keys($remote);
 if($_GET['key'])
 {$success=irsend("SEND_ONCE",$remote,$_GET['key']);
  for ($i = 0; $i < count($success);$i++)
  {print("<br> sent key " . $success[$i] . "<br>\n");
}}}
else
{$remotes=irsend("list","","");
 for ($i = 0; $i < count($remotes);$i++)
 {list($irsend,$remotename)=split(" ",$remotes[$i]);
  print("<a href=?remote=" . $remotename . ">" . $remotename . "<br>\n");
 }
}
?>
