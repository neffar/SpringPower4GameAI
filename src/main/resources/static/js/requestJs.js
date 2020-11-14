$(document).ready(() => {

    const dif = document.querySelector("#dif");

    document.addEventListener('input', function (event) {

        // Only run on our select menu
        if (event.target.id !== 'dif') return;

        window.location.href = "/profondeur/" + dif.value;

        /*
        $.ajax({
            type: "POST",
            url: "/level",
            data: {profondeur: dif.value},
            cache: false,
            timeout: 600000,
            success: data => {
                console.log(data)
            },
            error: e => {
                console.log("ERROR : ", e);
            }
        })
         */

    }, false);

    clickOnCol = colNum => {

        const url = '/play/' + colNum;

        $("#resultsBlock").load(url);
    }
});