# DCWEB-SOBA
Deep Contextual Word Embeddings-Based Semi-Automatic Ontology Building for Aspect-Based Sentiment Classification

Creating word embeddings
   1. Create word embeddings using bert-base-uncased model from the huggingface library, the code is given under the file name : wordEmbeddingCode.ipynb
   2. Create sentiment-aware word embeddings by first training the bert-base-uncased model on sequence classification using BertforSequenceClassification, the weights    obtained can be implemented thereafter in a bert model to create word embeddings with the trained model, the code is given under the file name : 
