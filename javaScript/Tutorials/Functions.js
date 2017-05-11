var myFunction = function(){
	console.log("Here is my first funciton whoop");
};

myFunction.firsName = "Fergal";
console.log(myFunction.firsName);

//Example of 'static' calls within javascript 
// 	ie: where you only want one instance of an object defined

var Person = function(){
this.firstName='Fergal';

}

Person.findOne = function(){


}		// Static Method;



//Anonymous Functions

setTimeout(function(){
	console.log('Hey this is an Anonymous Function Call ');
},1000);


//Below is a self executing function call;
(function() {
})(); 

//Working example of above 'contained Logic within its own scope'
// in other words not accessible to the rest of the file
//(function() {

//setTimeout(function(){
//	console.log('Hey this is an Anonymous Function Call ');
//},1000);


//})();

// Showing scope

// This example is incorrect

(function() {

var messages = ['hello', 'hows it going'];
	for(var i in messages){
		setTimeout( function(){
		console.log(messages[i]);
		},10);
	}
})();

// in this case i is only viewable from inside the for loop and not within the function...
// because of the delay in setTimeout function i has already passed through the loop...
// once i is passed through once it increases in value and prints the value of one...twice
// The solution is below
(function() {

var messages = ['hello', 'hows it going'];
	var say = function(i){	setTimeout( function(){
		console.log(messages[i]);
		},1);
	};
	
	for(var i in messages){
		say(i);
	}
})();
