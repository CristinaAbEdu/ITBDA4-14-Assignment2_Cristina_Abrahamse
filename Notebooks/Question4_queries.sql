-- Investigation 1: Average book price by rating

SELECT
    rating,
    COUNT(*) AS number_of_books,
    ROUND(AVG(price_gbp), 2) AS average_price_gbp
FROM clean_books
GROUP BY rating
ORDER BY rating;


-- Investigation 2: Five most expensive books

SELECT
    title,
    price_gbp,
    rating
FROM clean_books
ORDER BY price_gbp DESC
LIMIT 5;