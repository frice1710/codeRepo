var person = {
	firstName:'Fergal',
	lastName:'Rice',
	getName: function(){
		return this.firstName + ' ' + this.lastName;
	},
	address:{
		zip:12345,
		street:'Mount Prospect Avenue'

	}

	};


console.log(person.address.street);