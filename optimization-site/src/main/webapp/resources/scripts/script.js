var countLimitNow = 0;
function addInputTextLimits() {

	var countLimit = document.getElementById("countLimit").value;
	var container = document.getElementById("functionForm");
    //validate
    var errorCountLimit = document.getElementById("errorCountLimit");
    if(countLimit <= 0){
        errorCountLimit.innerHTML = "Количество ограничений не может быть нулевым или отрицательным";
        return false;
    } else
    errorCountLimit.innerHTML = "";

	// Clear previous contents of the container
            for (i=0;i<countLimitNow;i++){
                container.removeChild(container.lastChild);
            }
            countLimitNow = countLimit * 3 + 2;
            var p = document.createElement('div');
			p.innerHTML = "Ограничения";
            container.appendChild(p);
            for (i=0;i<countLimit;i++){
                // Append a node with a random text
                //container.appendChild(document.createTextNode("Member " + (i+1)));
                // Create an <input> element, set its type and name attributes
                var input = document.createElement("input");
                input.type = "text";
                input.name = "limit" + i;
                input.id = "limit" + i;
                container.appendChild(input);
                var spanError = document.createElement("span");
                spanError.name = "errorLimit" + i;
                spanError.id = "errorLimit" + i;
                spanError.className = "error";
                container.appendChild(spanError);
                // Append a line break 
                container.appendChild(document.createElement("br"));
            }
            var input = document.createElement("input");
                input.type = "submit";
                input.name = "calculate";
                input.value = "Вычислить";
                container.appendChild(input);
}

function changeSetting(){
    var countChromosomeInGeneration = document.getElementById("countChromosomeInGeneration").value;
    var countChromosomeInGenerationHidden = document.getElementById("countChromosomeInGenerationHidden");
    countChromosomeInGenerationHidden.value = countChromosomeInGeneration;
    var countGeneration = document.getElementById("countGeneration").value;
    var countGenerationHidden = document.getElementById("countGenerationHidden");
    countGenerationHidden.value = countGeneration;
}

function validateLimits() {
    var flag = true;
    var countLimit = document.getElementById("countLimit").value;
    for(var i = 0; i < countLimit; i++){
        if(!validateLimit(i)) flag = false;
    }
    return flag;
}

function validateLimit(i) {
    var limit = document.getElementById("limit"+i).value;
    var error = document.getElementById("errorLimit" + i);
    var reg = "([1-9]*[ ]?[\\*]*[ ]?[a-zA-z1-9]+[^1-9]*[ ]?[\\+|-]?[ ]?){1,}(>=|=|<=){1}[ ]?[1-9]+";
    if(limit.match(reg)){
        error.innerHTML = "";
        return true;
    }
    error.innerHTML = "Неправильно введено математическое неравенство";
    if(limit == '') error.innerHTML = "Введите значение в поле";
    return false;
}

function validateTargetFunction() {
    var targetFunction = document.getElementById("targetFunction").value;
    var error = document.getElementById("errorTargetFunction");
    var reg = "([1-9]+[ ]?[\\*]{1}[ ]?[a-zA-z1-9]+[^1-9]*[ ]?[\\+|-]?[ ]?){1,}";
    if(targetFunction.match(reg)){
        error.innerHTML = "";
        return true;
    }else{
        error.innerHTML = "Неправильно введена математическая функция";
        if (targetFunction == '') error.innerHTML = "Введите значение в поле";
    }
    return false;
}

function validate() {
    var flag = true;
    if(!validateTargetFunction()) flag = false;
    if(!validateLimits()) flag = false;
    return flag;
}



