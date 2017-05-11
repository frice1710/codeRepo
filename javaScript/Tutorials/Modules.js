

//Closures

var Alert = (function() {
	var messages = [];    // this becomes private if it is not 'returned' within the function
	return {
		add: function(message){
			messages.push(message)
		},
		log: function(){
				console.log(messages);
		}

	}
})();

console.log(Alert);
Alert.add('Hey');
Alert.add('Fergal');

Alert.log();