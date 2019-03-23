package framework.response;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlResponse extends Response {
    private static final String VIEWS_FOLDER = "src/main/java/app/views/";
    private File template;
    private HashMap<String, String> variables;
    private HashMap<String, String> header;

    public HtmlResponse(String templateFile) {
        this(templateFile, new HashMap<String, String>());
    }

    public HtmlResponse(String templateFile, HashMap<String, String> variables) {
        this.template = new File(VIEWS_FOLDER+templateFile);
        this.variables = variables;
        this.header = new HashMap<String, String>();
        this.header.put("Content-Type", "");
    }

    private String replaceVariables(String rawHtml) {

        Pattern pattern = Pattern.compile("<%=(.*?)%>");
        Matcher matcher = pattern.matcher(rawHtml);

        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            if(variables.get(matcher.group(1).trim()) == null) {
                // TODO: throw Exception
            }
            matcher.appendReplacement(sb, this.variables.get(matcher.group(1).trim()));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    public String render() {
        Scanner scanner = null;
        StringBuilder responseContext = new StringBuilder();

        responseContext.append("HTTP/1.1 200 OK\n");
        for (String key :
                this.header.keySet()) {
            responseContext.append(key).append(":").append(this.header.get(key)).append("\n");
        }
        responseContext.append("\n");
        try {
            scanner = new Scanner(this.template);
            StringBuilder fileContext = new StringBuilder();

            while(scanner.hasNextLine()) {
                fileContext.append(scanner.nextLine());
            }

            responseContext.append(replaceVariables(fileContext.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return responseContext.toString();
    }
}
