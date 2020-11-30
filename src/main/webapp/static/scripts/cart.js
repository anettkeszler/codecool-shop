import {postApi, getApi} from "./data_handler.js";

const addToCartButtons = document.querySelectorAll(".card__add-to-cart-button");
console.log(addToCartButtons)
addToCartButtons.forEach(button => {

    button.addEventListener("click", event => {
        postApi(`cart?product-id=${event.target.id}`, event.target.id, renderData)
    })
})

function renderData(data) {
    const shoppingCart = document.querySelector("#shopping-cart__items");
    console.log(shoppingCart);
    console.log(data);
    shoppingCart.innerHTML = '';
    data.forEach(item => {
        shoppingCart.innerHTML +=
        `
        <div class="buttons">
            <span class="delete-btn"><i class="fas fa-times"></i></span>
            <span class="like-btn"><i class="fas fa-heart"></i></span>
        </div>

        <div class="description" id="shopping-cart-modal">
            <span>${item.name}</span>
            <span>${item.defaultPrice} ${item.defaultCurrency}</span>
        </div>

            <div class="quantity">
                <button class="plus-btn" type="button" name="button">
                    <i class="fas fa-plus"></i>
                </button>
                <span>${item.quantity}</span>
                <button class="minus-btn" type="button" name="button">
                    <i class="fas fa-minus"></i>
                </button>
        </div>
        `
    })
    shoppingCart.innerHTML +=
        `
        <div class="total-price">$0</div>
        `
}