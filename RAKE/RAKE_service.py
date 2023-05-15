from flask import Flask, request, jsonify
from RAKE import extract_keywords
from flask_cors import CORS, cross_origin

app = Flask(__name__)
CORS(app)


@cross_origin
@app.route("/extract", methods=["POST"])
def handle_extraction():
    comments_json = request.json
    keywords = extract_keywords(list(comments_json.values()), 10)
    return jsonify(keywords)


if __name__ == "__main__":
    app.run(port=5000, debug=True)
