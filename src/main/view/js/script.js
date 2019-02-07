function Node(id, name, nodes) {
    this.id = id;
    this.name = name;
    this.nodes = nodes;
    this.open = false;
    this.level = 0;
}

function createElement(element, node) {
    var newNode = document.createElement('div');
    $(newNode).addClass('node');
    $(newNode).addClass('lvl' + node.level);
    $(newNode).attr('id', node.id);
    $(newNode).data('value', node);
    $(newNode).off("click");
    $(newNode).on("click", onNodeClick);

    var inner = document.createElement('div');
    $(inner).addClass('inner');
    $(inner).text("ID: " + node.id + "   - " + node.name);

    newNode.append(inner);
    element.append(newNode);
}

function onNodeClick(e) {
    var node = $(this).data('value');
    var element  = $(this);

    !node.open ?
        node.nodes.forEach(function(n) {
            n.level = node.level + 1;
            createElement(element, n);
        }) : element.children().remove(".node");

    node.open = !node.open;
    e.stopPropagation();
}

function init(jsonpath) {
    $.getJSON(jsonpath, function( data ) {
        data.forEach(function(n) {createElement($('#tree'), new Node(n.id, n.name, n.nodes))})
    });
}

var JSON_PATH = "../../../out.json";
init(JSON_PATH);