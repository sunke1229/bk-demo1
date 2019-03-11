$(function($){
  var codeText ="#!/usr/bin/env bash\n" +
        "set -o nounset                              # Treat unset variables as an error\n" +
        "\n" +
        "export LC_ALL=C\n" +
        "export LANG=C\n" +
        "\n" +
        "usage() {\n" +
        "    echo \"Usage: $0 [-h] [-i ip] -p <port>;\"\n" +
        "}\n" +
        "\n" +
        "precheck() {\n" +
        "    if ! which ss &> /dev/null; then\n" +
        "        echo \"ss not found, try install iproute2 package first\"\n" +
        "        exit 1\n" +
        "    fi\n" +
        "\n" +
        "    if [[ $( id -u ) != 0 ]]; then\n" +
        "        echo \"$0 should run under root\"\n" +
        "        exit 1\n" +
        "    fi\n" +
        "}";
  var editor = CodeMirror.fromTextArea(document.getElementById("editor2_demo"), {
    lineNumbers: true,
    mode: "shell",
		matchBrackets: true,
		theme:'monokai', //编辑器主题
		readOnly:true
  });
  editor.setValue(codeText);
})

// 脚本来源
$('input[name=scriptFrom]:radio').click(function(){
	var type = this.value;		 
	if(type == 1){
		$('#commonScript').hide(400);
		$('#inspectionTemplate').show(400);
	}
	else if(type == 2 ){
		$('#commonScript').show(400);
		$('#inspectionTemplate').hide(400);
	}
});