DCWEB-SOBA

Deep Contextual Word Embeddings-Based Semi-Automatic Ontology Building for Aspect-Based Sentiment Classification

In this github we provide the codes necessary to construct an ontology from the DCWEB-SOBA ontology builder. The ontology builder uses deep-contextual word embeddings to create a more accurate ontology. The word embeddings are programmed in Python while the ontology framework is programmed in Java.

Creating word embeddings

   -  Create word embeddings using bert-base-uncased model from the huggingface library, the code is given under the file name : wordEmbeddingCode.ipynb
   -  Create sentiment-aware word embeddings by first training the bert-base-uncased model on sequence classification using BertforSequenceClassification, the      weights obtained can be implemented thereafter in a bert model to create word embeddings with the trained model, the code is given under the file name : BertFineTunedModel.ipynb
   -  A 2-dimensional representation of the vectors can be obtained by using the t-SNE code : VisualizationT_SNE.ipynb

Ontology framework Load the data in TermSelectionAlgo and create the map with finetuned and pre trained vectors. This is done with several methods that only work with the format of the bert word embeddings. After the map is saved, un-bracket the other code in the constructor to work with this map and perform the term selection.

The ontology obtained from DCWEB-SOBA can be used in aspect-based sentiment classification (ABSA). We recommend the following frameworks for evaluation: Heracles and HAABSA++. Heracles is more user friendy, whereas HAABSA++ provides better results.

The ontology was built using the Yelp dataset on restaurant reviews(https://www.yelp.com/dataset)

Our method DCWEB-SOBA is related to the following papers:

- Haaf, F.t., Claassen, C., Enschauzier, R., Tjan, J., Buijs, D., Frasincar, F.,Schouten, K.: WEB-SOBA: Word embeddings-based semi-automatic ontologybuilding for aspect-based sentiment classification. In: 18th Extended Semantic WebConference (ESWC 2021). LNCS, Springer (to appear) (2021)
- Dera, E., Frasincar, F., Schouten, K., Zhuang, L.: Sasobus: Semi-automatic sentiment domain ontology building using synsets. In: European Semantic Web Conference. pp. 105–120. Springer (2020)
- Schouten, K., Frasincar, F.: Ontology-driven sentiment analysis of product and service aspects. In: 15th Extended Semantic Web Conference (ESWC 2018). LNCS, vol. 10843, pp. 608–623. Springer (2018)
- Wallaart, O., Frasincar, F.: A hybrid approach for aspect-based sentiment analysis using a lexicalized domain ontology and attentional neural models. In: 16th Extended Semantic Web Conference (ESWC 2019). LNCS, vol. 11503, pp. 363–378. Springer (2019) Zhuang, L., Schouten, K., Frasincar, F.: Soba: Semi-automated ontology builder for aspect-based sentiment analysis. Journal of Web Semantics 60, 100–544 (2020)
