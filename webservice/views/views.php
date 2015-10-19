<?php
header("Access-Control-Allow-Origin: *");

class ApiView {
    }

class JsonView extends ApiView
{
    // Returns key-value JSON objects
    public function render($content)
    {
        header('Content-Type: application/json');
        echo json_encode($content);
        return true;
    }
}

class HtmlView extends ApiView
{
    // API HTML is rendered (with use of <pre> tags, meaning it displays data exactly as it's structured, not the UI)
    public function render($content)
    {
    header('Content-Type: text/html');
    echo "<pre>";
    print_r($content);
    echo "</pre>";
    return true;
    }
}
class XmlView extends ApiView
{
    // Returns XML for API
    public function render($content)
    {
    // Foreach loops form tree structure of XML doc.
    $simplexml = simplexml_load_string('<?xml version="1.0" ?><data />');
    foreach($content as $row)
    {
        foreach($row as $key => $value)
        {
            $simplexml->addChild($key, $value);
        }
    }
    header('Content-Type: text/xml');
    echo $simplexml->asXML();
    return true;
    }
}
?>