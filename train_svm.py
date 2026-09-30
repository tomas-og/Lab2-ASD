"""
train_svm.py

Trains a scikit-learn SVM model on the Zoo dataset and
exports it to ONNX format so it can be loaded from Java.

Steps:
  1. Load data/zoo.csv
  2. Split into features (16 columns) and label (class_type)
  3. Split into train/test sets
  4. Train an SVM classifier using a linear kernel
  5. Print accuracy on the held-out test set
  6. Convert the trained model to ONNX and save it to models/

Run with:
  python train_svm.py
"""

import pandas as pd
from pathlib import Path

from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

from skl2onnx import convert_sklearn
from skl2onnx.common.data_types import FloatTensorType

DATA_FILE = Path(__file__).parent / "data" / "zoo.csv"
MODEL_OUT = Path(__file__).parent / "models" / "zoo_svm.onnx"

# The 16 input features, in a fixed order. This exact order matters:
# it must match the order the Java code builds its input tensor in.
FEATURE_COLUMNS = [
    "hair", "feathers", "eggs", "milk", "airborne", "aquatic",
    "predator", "toothed", "backbone", "breathes", "venomous", "fins",
    "legs", "tail", "domestic", "catsize",
]

LABEL_COLUMN = "class_type"


def main() -> None:
    # 1. Load data
    df = pd.read_csv(DATA_FILE)
    X = df[FEATURE_COLUMNS].astype("float32")
    y = df[LABEL_COLUMN].astype("int64")

    # 2. Train/test split (stratify keeps class proportions similar in
    # both sets, which matters here since some classes have only 4-5
    # examples in the whole dataset)
    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.25, random_state=42, stratify=y
    )

    # 3. Train the model.
    model = SVC(
        kernel="linear",
        probability=True,
        random_state=42
    )
    model.fit(X_train, y_train)

    # 4. Evaluate
    predictions = model.predict(X_test)
    accuracy = accuracy_score(y_test, predictions)
    print(f"SVM test accuracy: {accuracy:.2%}")

    # 5. Convert to ONNX.
    # The input is a single tensor of 16 float32 values per animal.
    # "None" in the shape means "batch size can be anything".
    # zipmap=False keeps the probability output as a plain
    # [batch, num_classes] float tensor instead of a sequence of
    # label->probability maps. The plain tensor is much simpler
    # to read from Java.
    initial_type = [("input", FloatTensorType([None, len(FEATURE_COLUMNS)]))]
    onnx_model = convert_sklearn(
        model,
        initial_types=initial_type,
        target_opset=17,
        options={id(model): {"zipmap": False}},
    )

    MODEL_OUT.parent.mkdir(parents=True, exist_ok=True)
    with open(MODEL_OUT, "wb") as f:
        f.write(onnx_model.SerializeToString())

    print(f"Saved ONNX model to {MODEL_OUT}")


if __name__ == "__main__":
    main()

