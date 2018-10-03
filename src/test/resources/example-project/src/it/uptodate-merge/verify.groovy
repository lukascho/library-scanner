File file = new File( basedir, "build.log" );
assert file.exists();

String text = file.getText("utf-8");

assert !text.contains('define property propname-merge = "outofdate"') : "Erroneously defined property propname-merge = \"outofdate\""
assert text.contains('define property propname-merge = "uptodate"') : "Failed to define property propname-merge = \"uptodate\""

return true;
