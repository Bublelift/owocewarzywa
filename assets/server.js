const express = require('express');
const { readFileSync } = require('fs');

const PORT = 8080;
const app = express();

let words =
    [
        {
            "image": "img/kaczka.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "kaczka",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "duck",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/pies.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "pies",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "dog",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/kot.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "kot",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "cat",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/żyrafa.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "żyrafa",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "giraffe",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/słoń.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "słoń",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "elephant",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/papuga.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "papuga",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "parrot",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/wiewiórka.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "wiewiórka",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "squirrel",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/chomik.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "chomik",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "hamster",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/foka.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "foka",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "seal",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/motyl/jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "motyl",
                    "category": "zwierzęta"
                },
                {
                    "language": "english",
                    "name": "butterfly",
                    "category": "animals"
                }
            ]
        },
        {
            "image": "img/słońce.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "słońce",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "sun",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/chmura.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "chmura",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "cloud",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/drzewo.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "drzewo",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "tree",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/góry.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "góry",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "mountains",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/kwiat.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "kwiat",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "flower",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/tęcza.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "tęcza",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "rainbow",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/morze.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "morze",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "sea",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/rzeka.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "rzeka",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "river",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/jezioro.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "jezioro",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "lake",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/plaża.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "plaża",
                    "category": "krajobraz"
                },
                {
                    "language": "english",
                    "name": "beach",
                    "category": "landscape"
                }
            ]
        },
        {
            "image": "img/drzwi.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "drzwi",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "door",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/krzesło.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "krzesło",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "chair",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/stół.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "stół",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "table",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/kubek.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "kubek",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "cup",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/łyżka.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "łyżka",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "spoon",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/nóż.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "nóż",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "knife",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/widelec.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "widelec",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "fork",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/podłoga.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "podłoga",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "floor",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/ściana.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "ściana",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "wall",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/dach.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "dach",
                    "category": "dom"
                },
                {
                    "language": "english",
                    "name": "roof",
                    "category": "house"
                }
            ]
        },
        {
            "image": "img/cebula.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "cebula",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "onion",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/jabłko.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "jabłko",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "apple",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/pomarańcza.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "pomarańcza",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "orange",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/winogrono.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "winogrono",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "grape",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/ogórek.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "ogórek",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "cucumber",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/marchewka.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "marchewka",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "carrot",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/pomidor.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "pomidor",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "tomato",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/ziemniak.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "ziemniak",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "potato",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/banan.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "banan",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "banana",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/ananas.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "ananas",
                    "category": "warzywa i owoce"
                },
                {
                    "language": "english",
                    "name": "pineapple",
                    "category": "vegetables and fruits"
                }
            ]
        },
        {
            "image": "img/samochód.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "samochód",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "car",
                    "category": "mode of transport"
                }
            ]
        },
        {
            "image": "img/rower.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "rower",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "bike",
                    "category": "mode of transport"
                }
            ]
        },
        {
            "image": "img/motocykl.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "motocykl",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "motorcycle",
                    "category": "mode of transport"
                }
            ]
        },
        {
            "image": "img/deskorolka.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "deskorolka",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "skateboard",
                    "category": "mode of transport"
                }
            ]
        },
        {
            "image": "img/ciężarówka.jpg",
            "level": "easy",
            "languages": [
                {
                    "language": "polish",
                    "name": "ciężarówka",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "truck",
                    "category": "mode of transport"
                }
            ]
        },
        {
            "image": "img/autobus.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "autobus",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "bus",
                    "category": "mode of transport"
                }
            ]
        },
        {
            "image": "img/rolki.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "rolki",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "rollers",
                    "category": "mode of transport"
                }
            ]
        },
        {
            "image": "img/samolot.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "samolot",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "plane",
                    "category": "mode of transport"
                }
            ]
        },
        {
            "image": "img/sterowiec.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "sterowiec",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "airship",
                    "category": "mode of transport"
                }
            ]
        },
        {
            "image": "img/pociąg.jpg",
            "level": "hard",
            "languages": [
                {
                    "language": "polish",
                    "name": "pociąg",
                    "category": "środek transportu"
                },
                {
                    "language": "english",
                    "name": "train",
                    "category": "mode of transport"
                }
            ]
        }
    ]

let fill_in_blanks = [
    {
        "level": "easy",
        "language": "polish",
        "category": "wyliczanki",
        "text": "Chodzi lisek koło drogi, nie ma ręki ani nogi, ani głowy, ani pupy, daj liskowi talerz zupy! Raz, dwa, trzy, cztery Maszerują oficery ptaszek myślał, że to żołnierz i narobił mu na kołnierz. Palec pod budkę, bo za minutkę zamykam budkę. Budka zamknięta nie ma klienta, klient w Warszawie siedzi na ławie."
    },
    {
        "level": "easy",
        "language": "english",
        "category": "negative structures with think, suppose, etc",
        "text": "Are you going to post these photos on Facebook? I don't think so. I don't use Facebook any more. I don't feel that everyone needs to know what i'm doing all the time. Seriously? I didn't think there was anyone who wasn't on Facebook! You're probably the only one. I'm not so sure. I don't expect I'm the only person who doesn't want to have a huge digital footprint. Well, I guess not, but I don't suppose many people actually think about their digital footprint. Yeah, I don't imagine so, but people really shouldn't give out so much personal information. It leaves them open to problems. True. I guess I shouldn't post everything I do."
    },
    {
        "level": "hard",
        "language": "polish",
        "category": "łamańce językowe",
        "text": "W Szczebrzeszynie chrząszcz brzmi w trzcinie i Szczebrzeszyn z tego słynie. Wół go pyta: Panie chrząszczu, po co pan tak brzęczy w gąszczu? Jak to – po co? To jest praca, każda praca się opłaca. A cóż za to Pan dostaje? Też pytanie! Wszystkie gaje, wszystkie trzciny po wsze czasy, łąki, pola oraz lasy, nawet rzeczki, nawet zdroje, wszystko to jest właśnie moje!"
    },
    {
        "level": "hard",
        "language": "english",
        "category": "life skills",
        "text": "We are surrounded by claims. Almost every day in the news you hear stories and statistics that seem to show that A causes B. There are countless examples of headlines claiming that this food causes cancer, or that food prevents it. Understanding and analysing these claims is an important part of mdern life, but we rarely teach it in schools."
    },
    {
        "level": "easy",
        "language": "polish",
        "category": "łamańce językowe",
        "text": "Pocztmistrz z Tczewa zastanawia się, czy się Czesi cieszą, że się Czesio czesze? Ząb zupa zębowa, dąb zupa dębowa. Czego trzeba strzelcowi do zestrzelenia cietrzewia drzemiącego w dżdżysty dzień na drzewie?"
    },
    {
        "level": "easy",
        "language": "english",
        "category": "animal issues",
        "text": "If pet shops are not allowed to sell wild species, wild animals will be bought from smugglers or other illegal sources. However, if people buy animals in pet shops, and if pet shops buy from authorised breeders, the illegal capture and sale of wild animals can be stopped, or at least reduced. It is also a fact that some pet shops help educate people about animals, both domestic and wild. Anyone who buys an animal at a good shop will be given detailed instructions for taking care of it."
    },
    {
        "level": "hard",
        "language": "polish",
        "category": "kolory",
        "text": "Kasia ma oczy koloru brązowego. Jej ulubiony kolor to brązowy. Zawsze jak jest w sklepie, to kupuje jedną pomarańczę. Katarzyna opiekuje się kotką o imieniu Pomarańcza. W wolnych chwilach biegają razem po zielonej łące podziwiając zielone połacie traw. Gdy niebo zmieni kolor z niebieskiego na odcienie czerwieni, Kasia razem z Pomarańczą idą do przytulnego domu."
    },
    {
        "level": "hard",
        "language": "english",
        "category": "animal issues",
        "text": "I know that most of the wild animals sold in pet shops are bred in captivity, and in most cases these animals could never be released into the wild, but that does not mean that they make good pets. Many people buy cute baby animals and then do not know what to do with them when they grow up and begin to exhibit behaviours like biting or scratching. Furthermore, many animals are still caught in the wild, which damages wild populations and causes trauma and suffering for the animals that are caught."
    },
    {
        "level": "easy",
        "language": "polish",
        "category": "łamańce językowe",
        "text": "Gdy pomorze nie pomoże, to pomoże może morze, a gdy morze nie pomoże, to pomoże może las. Nie pieprz wieprza pieprzem Pietrze, bo bez pieprzu wieprz jest lepszy. Król Karol kupił królowej Karolinie korale koloru koralowego."
    },
    {
        "level": "easy",
        "language": "english",
        "category": "robotics",
        "text": "No one can deny that the past few years have seen some impressive developments in robotics, and the future looks even brighter for robot technology. My research is focused on using robots for search and rescue missions and enviromental clean-ups. For example, in the next few years, robots will have been developed that can that can instantly stop oils spills or radiation leaks, helping to prevent disasters."
    },
    {
        "level": "hard",
        "language": "polish",
        "category": "łamańce językowe",
        "text": "Nazywam się Grzegorz Brzęczyszczykiewicz. Mieszkam w Chrząszczyżewoszycach, w powiecie Łękołody. Niestety mój stół posiada powyłamywane nogi, a mój rewolwer jest wyrewolwerowany. Ostatnio zadaję sobie pytanie, czy jest Jola lojalna, czy Jola nielojalna"
    },
    {
        "level": "hard",
        "language": "english",
        "category": "robotics",
        "text": "Though robots definatelly have their issues, I am concerned about investing much money into programmers aimed at machines instead of people. Robots are enormously expensive. There will be a significant shortage or healthcare workers by the end of this decade, largely because of lack of money. Robots can never replace trained personnel. Certainly they can play like a key part in rescue efforts, for example, but robots can't make the kinds of independent decisions that people can."
    }
]

let minigames = {
    'unscramble': { minigame: 'unscramble' },
    'memorygame': { minigame: 'memorygame' },
    'puzzles': { minigame: 'puzzles' }
}

let languages = {
    'polish': { language: 'polish' },
    'english': { language: 'english' }
}

let levels = {
    'hard': { level: 'hard' },
    'easy': { level: 'easy' }
}

let categories = {
    'animals': { category: 'animals' },
    'landscape': { category: 'landscape' },
    'house': { category: 'house' },
    'vegetables and fruits': { category: 'vegetables and fruits' },
    'mode of transpor': { category: 'mode of transport' },
    'robotics': {category: 'robotics'},
    'animal issues': {category: 'animal issues'},
    'life skills': {category: 'life skills'},
    'negative structures with think, suppose, etc': {category: 'negative structures with think, suppose, etc'}
}

let tasks = {
    'quiz': { task: 'quiz' },
    'flashcards': { task: 'flashcards' },
    'fill-in-blanks': { task: 'fill-in-blanks' }
}

app.get('/api/users', (req, res) => {
    let minigame = req.query.minigame
    let task = req.query.task
    let language = req.query.language
    let level = req.query.level
    let category = req.query.category
    var filtered_words = []
    var j = 0
    //sprawdzanie, czy dane parametry istnieją
    if (minigames[minigame] && languages[language] && levels[level] && categories[category]) {
        var languagesSize = 2
        for (var i = 0; i < words.length; i++) {
            for (var y = 0; y < languagesSize; y++) {
                if (words[i].level == level && words[i].languages[y].language == language && words[i].languages[y].category == category) {
                    filtered_words[j] = words[i]
                    j++
                }
            }
        }
        res.send(filtered_words)
    }
    else if (tasks[task] && languages[language] && levels[level] && categories[category]) {
        if (task == 'fill-in-blanks') {
            for (var i = 0; i < fill_in_blanks.length; i++) {
                if (fill_in_blanks[i].level == level && fill_in_blanks[i].language == language && fill_in_blanks[i].category == category) {
                    filtered_words[j] = fill_in_blanks[i]
                    j++
                }
            }
            res.send(filtered_words)
        }
        else {
            for (var i = 0; i < words.length; i++) {
                for (var y = 0; y < languagesSize; y++) {
                    if (words[i].level == level && words[i].languages[y].language == language && words[i].languages[y].category == category) {
                        filtered_words[j] = words[i]
                        j++
                    }
                }
            }
            res.send(filtered_words)
        }
    }
    else { res.json('Parameters not found') }
});

app.listen(PORT);

module.exports = app;