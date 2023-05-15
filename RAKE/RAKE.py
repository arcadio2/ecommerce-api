import nltk
from nltk.corpus import stopwords
import numpy as np

# preparing stopwords
# Loading data...
# nltk.download("stopwords")
stop_words = list(stopwords.words("spanish"))
stop_words.append("éste")
stop_words.append("ahí")
stop_words.append("")
punctuation = set(";,:.?!")
for sign in punctuation:
    stop_words.append(sign)


def tokenize(text: str):
    punctuation = set(";,:.?!")
    for sign in punctuation:
        text = text.replace(sign, f" {sign}")
    tokens = text.lower().split(" ")
    return tokens


def get_candidate_keywords(text: list[str], stop_words):
    candidate_keywords = []
    phrase = ""
    for word in text:
        if word in stop_words:
            if phrase != "":
                # if a stop_word is found, we append the current phrase to our list
                candidate_keywords.append(phrase.strip())
                phrase = ""
                continue
            continue
        # let's continue adding the current word until a stopword is found
        phrase += word + " "
    return candidate_keywords


def count_frequency(tokenized: list[str]):
    frequencies = {}
    # initialize keys of dictionary
    for token in tokenized:
        frequencies[token] = 0
    # count each token
    for token in tokenized:
        frequencies[token] += 1
    return frequencies


def frequency_matrix(frequencies: dict, candidate_keywords: list[str]):
    l = len(frequencies.keys())
    indexes = {}
    matrix = np.zeros((l, l))
    # initialize keys of dictionary
    for i, key in enumerate(frequencies.keys()):
        indexes[key] = i

    # set frequency for each key individually
    for key, value in frequencies.items():
        idx = indexes[key]
        matrix[idx, idx] = value

    # set frequency for each apparison in a keyword
    for keyword in candidate_keywords:
        keyword_token = keyword.split(" ")
        if len(keyword_token) >= 2:
            idx1 = indexes[keyword_token[0]]
            idx2 = indexes[keyword_token[1]]
            matrix[idx1, idx2] += 1
    return matrix, indexes


def get_degree(frequency_matrix: np.ndarray, indexes):
    degree = {}
    for key, value in indexes.items():
        degree[key] = np.sum(frequency_matrix[:, value])
    return degree


def get_score(frequencies, degree, candidate_keywords):
    score = {}
    keys = list(frequencies.keys())
    # score for each individual word
    for key in keys:
        score[key] = degree[key] / frequencies[key]

    # score for each keyword
    for keyword in candidate_keywords:
        keyword_tokens = keyword.split(" ")
        if keyword not in keys:
            score[keyword] = 0
            for token in keyword_tokens:
                score[keyword] += score[token]

    # return sorted dictionary of scores
    score = {
        key: val
        for key, val in sorted(score.items(), key=lambda ele: ele[1], reverse=True)
    }
    return score


def extract_keywords(texts: list[str], n: int):
    tokenized_texts = []
    for text in texts:
        tokenized_texts.append(tokenize(text))
    # removing tokens that are stopwords
    clean = []
    # extracting candidate phrases
    candidate_phrases = []
    for tokenized in tokenized_texts:
        candidate_phrases = candidate_phrases + get_candidate_keywords(
            tokenized, stop_words
        )
        for word in tokenized:
            if not (word in stop_words):
                clean.append(word)

    frequencies = count_frequency(clean)

    matrix, indexes = frequency_matrix(frequencies, candidate_phrases)
    degree = get_degree(matrix, indexes)
    score = get_score(frequencies, degree, candidate_phrases)
    return [x for x in list(score)[:n]]
