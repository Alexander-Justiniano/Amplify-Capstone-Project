import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import random

# Load CSV files
albums_csv_path = "C:/Users/kg/Documents/samples/generated_albums.csv"
tracks_csv_path = "C:/Users/kg/Documents/samples/generated_tracks.csv"
df_albums = pd.read_csv(albums_csv_path)
df_tracks = pd.read_csv(tracks_csv_path)

# Merge DataFrames
df_combined = pd.merge(df_tracks, df_albums, left_on='album', right_on='album_name')
df_combined.rename(columns={'track': 'song'}, inplace=True)  # Ensure correct column names

# Feature vectorization
df_combined['combined_features'] = df_combined[['genre', 'artist', 'album']].apply(lambda x: ' '.join(x), axis=1)

def get_recommendations(df, song_name=None, year=None):
    if song_name is None or year is None:
        random_row = df.sample(n=1).iloc[0]
        song_name = random_row['song']
        year = random_row['release_year']

    print(f"Selected song: {song_name}, Year: {year}")
    
   # Fetch songs strictly by genre and create a copy immediately to avoid SettingWithCopyWarning when modifying
    genre_songs = df[df['genre'] == df.loc[df['song'] == song_name, 'genre'].values[0]].copy()
    genre_songs['year_difference'] = (genre_songs['release_year'] - year).abs()
    genre_songs_sorted = genre_songs.sort_values(by='year_difference')

    # Separate past and future songs
    past_songs = genre_songs_sorted[genre_songs_sorted['release_year'] < year].head(2)
    future_songs = genre_songs_sorted[genre_songs_sorted['release_year'] > year].head(3)

    # Combine past and future songs
    closest_songs = pd.concat([past_songs, future_songs])

    if not closest_songs.empty:
        # Apply TF-IDF vectorization and cosine similarity to the combined songs
        tfidf_vectorizer = TfidfVectorizer()
        tfidf_matrix = tfidf_vectorizer.fit_transform(closest_songs['combined_features'])
        cosine_sim = cosine_similarity(tfidf_matrix)

        # Assuming the selected song is part of the closest_songs DataFrame
        song_index = closest_songs.index[closest_songs['song'] == song_name].tolist()
        if song_index:
            song_index = song_index[0]
            sim_scores = list(enumerate(cosine_sim[song_index]))
            sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
            top_indices = [i[0] for i in sim_scores[1:6]]
            recommendations = closest_songs.iloc[top_indices]['song'].tolist()
        else:
            # If not found due to filtering, just pick the top 5 based on cosine similarity
            recommendations = closest_songs['song'].head(5).tolist()
    else:
        recommendations = []
        print("No recommendations found based on the criteria.")

    return recommendations, song_name, year

if __name__ == "__main__":
    try:
        recommended_songs, selected_song, selected_year = get_recommendations(df_combined, song_name=None, year=None)
        print(f"Recommendations for '{selected_song}' released around {selected_year}:")
        for song in recommended_songs:
            print(song)
    except ValueError as e:
        print("Error:", e)
    except Exception as e:
        print("An error occurred:", e)
