package com.gj.gb.factory;

import java.util.ArrayList;
import java.util.List;

import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.util.GBDataManager;

public class GBRecipeFactory {

	public static final GBRecipe getRecipeById(int id) {

		switch (id) {
		case 0:
			return new GBRecipe(
					id,
					"72OZ STEAK",
					"A steak is generally a cut of beef sliced perpendicular to the muscle fibers, or of fish cut perpendicular to the spine.",
					6, 6, 6);
		case 1:
			return new GBRecipe(
					id,
					"APPLE TARTIN",
					"Is an upside-down tart in which the fruit (usually apples) are caramelized in butter and sugar before the tart is baked.",
					0, 19);
		case 2:
			return new GBRecipe(
					id,
					"BANANA SPLIT",
					"Is an ice cream-based dessert. In its classic form it is served in a long dish called a boat.",
					2, 19);
		case 3:
			return new GBRecipe(id, "BEANS AND MUSHROOM NOODLES",
					"A simple noode.", 5, 26, 27);
		case 4:
			return new GBRecipe(
					id,
					"BEEF ENCHILADA",
					"Round beef and onion are wrapped in flour tortillas, topped with Cheddar cheese and black olives, then baked.",
					13, 11, 6);
		case 5:
			return new GBRecipe(
					id,
					"BISCOTTI",
					"Are twice-baked cookies (or biscuits) originating in the Italian city of Prato.",
					16, 14);
		case 6:
			return new GBRecipe(id, "BLEU VAMPIRE STEAK",
					"A very expensive steak.", 6, 33, 44);
		case 7:
			return new GBRecipe(id, "BRIGADERIOS",
					"Is a simple Brazilian chocolate bonbon.", 14, 8, 25);
		case 8:
			return new GBRecipe(
					id,
					"BRUSCHETTA WITH TOMATO AND BASIL",
					"Chopped fresh tomatoes with garlic, basil, olive oil, and vinegar, served on toasted slices of French.",
					7, 44, 3);
		case 9:
			return new GBRecipe(id, "BURGER AND FRIES", "A common snack.", 7,
					6, 33);
		case 10:
			return new GBRecipe(id, "CABBAGE ROLLS", "Sample", 36, 6, 9);
		case 11:
			return new GBRecipe(id, "CAESAR SALAD", "A common salad.", 38, 7,
					16);
		case 12:
			return new GBRecipe(
					id,
					"CAMEMBERT BAKED IN A BOX",
					" Vacherin, the deep, deliciously smelly cheese, is the usual one for baking whole in its box.",
					11, 11, 7);
		case 13:
			return new GBRecipe(
					id,
					"CARIBBEAN CHICKEN SALAD",
					"Work that skillet with the chicken and seasonings, and you'll cruise upon Island flavors to brag about without leaving home.",
					12, 5, 38);
		case 14:
			return new GBRecipe(id, "CHEESE BOARD", "A rare dish.", 11, 11, 11);
		case 15:
			return new GBRecipe(id, "CHEESE BURGER",
					"A hamburger topped with cheese.", 7, 6, 11);
		case 16:
			return new GBRecipe(
					id,
					"CHICKEN ADOBO",
					"Is an authentic Filipino dish and is one of the mostly recognized Filipino foods.",
					12, 17, 4);
		case 17:
			return new GBRecipe(id, "CHICKEN AND LEEK SOUP",
					"Is also known as Cock-a-Leekie Soup in Scotland.", 12, 21,
					21);
		case 18:
			return new GBRecipe(
					id,
					"CHICKEN TACOS",
					"Chicken chunks cooked with taco seasoning mix are layered in crisp taco shells with lettuce, cheese and tomato for an easy Mexican entrée.",
					42, 12, 38);
		case 19:
			return new GBRecipe(id, "CHICKEN TIKKA MASALA",
					"A dish of roasted chunks of chicken in a spicy sauce. ",
					12, 36, 4);
		case 20:
			return new GBRecipe(
					id,
					"CHILI CON CARNE",
					"More commonly known in American English as simply “chili”, is a spicy stew containing chili peppers, meat, tomatoes and often beans.",
					13, 5, 36);
		case 21:
			return new GBRecipe(id, "CHILI CRABS", "Spicy crab dish.", 15, 15,
					13);
		case 22:
			return new GBRecipe(id, "CHILI DOG",
					"A hotdog filled with chili sauce.", 32, 13);
		case 23:
			return new GBRecipe(id, "CHOCOLATE CAKE WITH ICECREAM",
					"A very delicious cake.", 14, 19);
		case 24:
			return new GBRecipe(id, "CORN ON COB", "A cornstick.", 42, 42, 8);
		case 25:
			return new GBRecipe(
					id,
					"CORNDOGS",
					"A hot dog sausage coated in a thick layer of cornmeal batter and deep fried in oil, although some are baked, especially to cut on fat and carbohydrates.",
					42, 8, 6);

		case 26:
			return new GBRecipe(
					id,
					"CRAB-STUFFED MUSHROOM",
					"Mushrooms stuffed with a crabmeat and bread crumb mixture are topped with cheese and baked in a buttery wine sauce.",
					15, 26, 28);
		case 27:
			return new GBRecipe(
					id,
					"CREAM CHEESE BAGEL",
					"A cream cheese bagel is a plain toasted bagel with cream cheese.",
					11, 7, 38);

		case 28:
			return new GBRecipe(
					id,
					"CREAM OF MUSHROOM SOUP",
					"A simple type of soup where a basic roux is thinned with cream or milk and then mushrooms and/or mushroom broth are added.",
					26, 25, 4);
		case 29:
			return new GBRecipe(
					id,
					"DIMSUM",
					"Cantonese food prepared as small bite-sized or individual portions of food traditionally served in small steamer.",
					34, 32);

		case 30:
			return new GBRecipe(
					id,
					"DONER KEBAB",
					"A Turkish dish made of meat cooked on a vertical spit, normally lamb but also a mixture of veal or beef with these, or sometimes chicken.",
					20, 28, 38);

		case 31:
			return new GBRecipe(
					id,
					"FALAFEL",
					"A deep-fried ball or patty made from ground chickpeas, fava beans.",
					5, 28, 17);

		case 32:
			return new GBRecipe(id, "FISH PIE", "A traditional British dish",
					45, 30, 33);

		case 33:
			return new GBRecipe(id, "FONDUE SET", "Cooking material.", 11, 0, 7);
		case 34:
			return new GBRecipe(id, "FRENCH FRIES",
					"Batons of deep-fried potato.", 33, 33, 11);
		case 35:
			return new GBRecipe(id, "FRUIT SELECTION", "A variety of fruits.",
					41, 0);
		case 36:
			return new GBRecipe(id, "GARDEN SALAD",
					"A salad consisting mostly of fresh vegetables.", 38, 44,
					16);
		case 37:
			return new GBRecipe(
					id,
					"GARLIC AND BUTTER PRAWNS",
					"Prawns cooked in butter, garlic, lemon juice and parsley are even better with the help of Dijon mustard.",
					17, 34, 8);
		case 38:
			return new GBRecipe(
					id,
					"GINGERBREAD HOUSE",
					"A sweet food-product flavored with ginger and typically using honey or molasses rather than just sugar modeled as a house.",
					18, 16, 7);

		case 39:
			return new GBRecipe(
					id,
					"GNOCCHI",
					"are various thick, soft pastas that may be made from semolina, ordinary wheat flour, flour, egg, and cheese.",
					29, 44, 3);

		case 40:
			return new GBRecipe(id, "GRILLED RUMP STEAK",
					"A simple steak dish.", 6, 33, 38);
		case 41:
			return new GBRecipe(
					id,
					"HAM AND CHEESE SANDWICH",
					"Made by putting cheese and sliced ham between two slices of bread. ",
					7, 32, 11);
		case 42:
			return new GBRecipe(
					id,
					"LAMB CASSEROLE",
					"A dish  with tomatoes and rice, with leftover cooked lamb, mushrooms, rice, tomatoes, and seasonings.",
					20, 26);

		case 43:
			return new GBRecipe(id, "LAMB SAMOSAS", "A rare lamb dish.", 20, 4);
		case 44:
			return new GBRecipe(id, "LAMB SKEWERS",
					"Lamb meat skewered and then grilled.", 20, 17, 22);

		case 45:
			return new GBRecipe(
					id,
					"LASAGNA",
					"A wide, flat pasta shape, and possibly one of the oldest types of pasta.",
					6, 11, 29);

		case 46:
			return new GBRecipe(id, "LEMON FIZZ", "Sample", 23, 22);
		case 47:
			return new GBRecipe(id, "LOBSTER", "Sample", 24, 8, 38);
		case 48:
			return new GBRecipe(id, "LOBSTER SOUP", "Sample", 24, 8, 22);
		case 49:
			return new GBRecipe(id, "LOBSTER THERMIDOR", "Sample", 24, 24, 16);
		case 50:
			return new GBRecipe(id, "MACARONI AND CHEESE", "Sample", 29, 11);
		case 51:
			return new GBRecipe(id, "MAPO TOFU", "Sample", 43, 36, 13);
		case 52:
			return new GBRecipe(id, "MEAT PLATTER", "Sample", 31, 32, 7);
		case 53:
			return new GBRecipe(id, "MEGA NACHOS", "Sample", 42, 13, 11);
		case 54:
			return new GBRecipe(id, "MISO SOUP", "Sample", 40, 5, 43);
		case 55:
			return new GBRecipe(id, "MONSTER BURGER", "Sample", 6, 6, 11);
		case 56:
			return new GBRecipe(id, "MUSHROOM RISOTTO", "Sample", 36, 26);
		case 57:
			return new GBRecipe(id, "ONION SOUP", "Sample", 28, 17, 4);
		case 58:
			return new GBRecipe(id, "PAD THAI", "Sample", 27, 34, 23);
		case 59:
			return new GBRecipe(id, "PASTEL", "Sample", 12, 11);
		case 60:
			return new GBRecipe(id, "PASTRAMI SANDWICH", "Sample", 6, 17, 7);
		case 61:
			return new GBRecipe(id, "PEA AND BACON SOUP", "Sample", 30, 30, 1);
		case 62:
			return new GBRecipe(id, "PHO SOUP", "Sample", 6, 27, 13);
		case 63:
			return new GBRecipe(
					id,
					"PIZZA CALZONE",
					"Tuck favorite pizza fillings between layers of tender crust, and save the pizza sauce for dipping.",
					26, 31, 21);
		case 64:
			return new GBRecipe(id, "PIZZA FLORENTINE", "A rare pizza.", 11,
					38, 16);
		case 65:
			return new GBRecipe(
					id,
					"PORK AND APPLE CHOPS",
					"Pork loin chops are paired with slices of tart apple cooked in a satin-smooth sauce of butter, sugar, cinnamon and nutmeg.",
					32, 0, 28);

		case 66:
			return new GBRecipe(id, "PRAWN SUSHI", "Prawn hand rolls.", 34, 40);
		case 67:
			return new GBRecipe(id, "PUMPKIN SOUP",
					"A usually bound soup made from a puree of pumpkin.", 35,
					13);
		case 68:
			return new GBRecipe(
					id,
					"RAINBOW SUSHI",
					"A name given to different sushi rolls with the common feature of having four different colored toppings on the roll.",
					45, 45, 36);
		case 69:
			return new GBRecipe(id, "RIB 'N' WINGS", "A delicious dish.", 32,
					12, 42);
		case 70:
			return new GBRecipe(id, "ROAST BEEF", "A roasted beef.", 6, 10, 38);
		case 71:
			return new GBRecipe(id, "ROAST CHICKEN", "A roasted chicken.", 12,
					33, 38);
		case 72:
			return new GBRecipe(
					id,
					"SAKURA MOCHI",
					"Is a variety of wagashi, or Japanese confectionery consisting of a sweet pink mochi",
					5, 36);
		case 73:
			return new GBRecipe(
					id,
					"SALMON HANDROLLS",
					"This tasty sushi roll is the perfect way to make use of that leftover salmon skin.",
					39, 36, 40);
		case 74:
			return new GBRecipe(id, "SALMON KEBABS WITH CHILI LIME GLAZE",
					"A very expensive dish.", 39, 23, 13);
		case 75:
			return new GBRecipe(
					id,
					"SALMON SASHIMI",
					"A Japanese delicacy consisting of very fresh raw meat or fish sliced into thin ",
					39, 39, 46);
		case 76:
			return new GBRecipe(
					id,
					"SCHINTZEL",
					"A boneless meat, thinned with a hammer (meat tenderizer), coated with flour, beaten eggs and bread crumbs, and then fried.",
					32, 7, 44);
		case 77:
			return new GBRecipe(id, "SCRAMBLED EGGS", "An egg dish.", 16, 8);
		case 78:
			return new GBRecipe(id, "SEAFOOD CHOWDER",
					"Lovely, thick and creamy chowder,", 39, 15, 25);
		case 79:
			return new GBRecipe(
					id,
					"SEAFOOD PAELLA",
					"A thin blanket of saffron-scented  rice with a crackly, crunchy bottom crust (or socarrat), studded with fresh seafood.",
					34, 36, 37);
		case 80:
			return new GBRecipe(id, "SEAFOOD PASTA",
					"A seafood flavored pasta.", 29, 34, 45);
		case 81:
			return new GBRecipe(id, "SEAFOOD PLATTER",
					"A platter filled with assorted seafoods.", 39, 15, 24);
		case 82:
			return new GBRecipe(id, "SEAWEED SALAD", "A healthy Japanese dish",
					40, 18, 13);
		case 83:
			return new GBRecipe(id, "SHRIMP ON THE BARBIE",
					"A very exotic dish.", 34, 22, 17);
		case 84:
			return new GBRecipe(
					id,
					"SMOKED SALMON PLATTER",
					" Smoked salmon with all the accoutrements, chopped onions, capers, sour cream, and chopped egg whites.",
					39, 39, 22);
		case 85:
			return new GBRecipe(id, "SOUR STRAWBERRY SWIRLIES",
					"A simple dessert.", 23, 41, 22);
		case 86:
			return new GBRecipe(
					id,
					"SPAGHETTI BOLOGNESE",
					"A classic bolognese sauce still tastes great, no matter how simple.",
					29, 44, 6);
		case 87:
			return new GBRecipe(id, "SPAGHETTI CARBONARA",
					"Is one of the most popular Italian pasta dishes. ", 29, 6,
					16);
		case 88:
			return new GBRecipe(
					id,
					"SPICY BUFFALO WINGS",
					"It is traditionally fried unbreaded and then coated in sauce.",
					12, 8, 13);
		case 89:
			return new GBRecipe(
					id,
					"STEAK AND CHIPS",
					"Steak and chips may seem a bit basic but done correctly, this dish is perfection.",
					6, 6, 33);
		case 90:
			return new GBRecipe(
					id,
					"STRAWBERRY CAKE",
					"A strawberry cake with a layer of cheesecake hidden in the middle and covered with strawberry.",
					8, 41);
		case 91:
			return new GBRecipe(
					id,
					"STRAWBERRY CHEESECAKE",
					"Made with cream cheese and a touch of lemon that's covered with a fresh, sweet, chunky strawberry sauce.",
					11, 41);
		case 92:
			return new GBRecipe(id, "STRAWBERRY SUNDAE",
					"A straberry flavored ice cream.", 19, 41, 41);
		case 93:
			return new GBRecipe(id, "SUNDAE", "A sweet ice cream dessert", 14,
					19);
		case 94:
			return new GBRecipe(id, "SWEET RICE CAKE",
					"A Japanese cake made from glutinous rice.", 36, 36);
		case 95:
			return new GBRecipe(id, "SWEETCORN SOUP",
					"A combination of sweetcorn and normal soup.", 42, 16);
		case 96:
			return new GBRecipe(id, "THE CHICKENATOR", "A chicken dish.", 12,
					12, 16);
		case 97:
			return new GBRecipe(
					id,
					"TIGER PRAWN PLATTER",
					"Beautifully presented prawn platter with 30 peeled and deveined Exmouth Tiger Prawns and a seafood cocktail sauce.",
					34, 17, 23);
		case 98:
			return new GBRecipe(
					id,
					"TOMATO AND BASIL SOUP",
					"Sweet vine-ripened tomatoes are the key to flavor in this creamy soup.",
					44, 3);
		case 99:
			return new GBRecipe(
					id,
					"TOMATO AND ONION TART",
					"This meatless main dish features flaky puff pastry topped with roasted tomatoes and onion, and lots of shredded mozzarella cheese.",
					44, 28);
		case 100:
			return new GBRecipe(id, "TUNA AND SWEETCORN PANINI",
					" A panini filled with tuna and sweetcorn.", 42, 45, 7);
		case 101:
			return new GBRecipe(id, "TUNA SALAD",
					" Tuna and mayonnaise or mayonnaise-substitute. ", 45, 29);
		case 102:
			return new GBRecipe(
					id,
					"TUNA STEAK",
					"A perfect candidate for grilling, and a sweet, tangy marinade keeps them moist and flavorful.",
					45, 33, 10);
		case 103:
			return new GBRecipe(
					id,
					"TUNA SUSHI",
					"A spicy hand roll (temaki), with avocado, cucumber and rice",
					36, 45, 46);
		case 104:
			return new GBRecipe(id, "VEGAN TOFU DELIGHT",
					"A dish filled with vegetables.", 43, 5, 38);
		case 105:
			return new GBRecipe(
					id,
					"VEGETABLE CHOWDER",
					"A variety of fresh vegetables are cooked in chicken broth and combined with a creamy cheddar cheese mixture.",
					25, 33, 30);
		case 106:
			return new GBRecipe(id, "VEGETARIAN FRIED RICE",
					"A very flavorful fried rice with color", 36, 21, 26);
		case 107:
			return new GBRecipe(
					id,
					"VEGETARIAN PLATTER",
					" Hummus, tabouli, feta cheese, stuffed grape leaves, calamata olives & cucumber slices",
					44, 38, 42);
		case 108:
			return new GBRecipe(
					id,
					"VEGETARIAN SOUP NOODLE",
					"A bowl of rice noodles, fresh herbs, and bean sprouts swimming in a fragrant broth. ",
					27, 26, 43);
		case 109:
			return new GBRecipe(id, "YAKI GYOZA",
					"A Japanese version of Chinese dumpling.", 32, 18, 17);
		case 110:
			return new GBRecipe(
					id,
					"YAKI UDON",
					"Yakiudon are thick, smooth, white Japanese noodles eaten with a special sauce, meat and vegetables.",
					27, 12);
		case 111:
			return new GBRecipe(id, "YAKIBUTA RAMEN",
					"Japanese noodle dish originated in China", 27, 32, 28);
		default:
			return null;
		}
	}

	public static List<GBRecipe> getAllAvailableRecipe() {
		GBGameData data = GBDataManager.getGameData();
		List<GBRecipe> list = new ArrayList<GBRecipe>();

		for (int i = 0; i < 112; i++) {
			GBRecipe recipe = getRecipeById(i);
			if (data.hasIngredient(recipe.getIngredients())) {
				list.add(recipe);
			}
		}

		return list;
	}
}
