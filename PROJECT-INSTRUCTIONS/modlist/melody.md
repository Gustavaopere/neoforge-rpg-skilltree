# Melody

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817f96fcc14dc79506d6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Melody
- **Arquivo JAR:** `melody_neoforge_1.0.10_MC_1.21.jar`
- **Versão 1.21.1:** 1.0.10
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Tipo de conteúdo:** Mod
- **Função:** Biblioteca OpenAL para áudio de fundo: AudioClip play/pause/resume/stop/volume/channel/close; criação OGG/WAV a partir de ResourceLocation, arquivo local ou web; listeners para volume e reload do SoundEngine. Não é sistema autônomo de música.
- **Dependências:** Minecraft 1.21.1 e NeoForge na build; Java 21. Consumer físico confirmado: FancyMenu. Não há dependência inversa de FancyMenu declarada por Melody; FancyMenu consome a API. Runtime do pack: NeoForge 21.1.248.
- **Sobreposição:** Sobrepõe parcialmente infraestrutura de reprodução de outros mods de áudio, mas não conteúdo musical. FancyMenu mantém authority da experiência/config que usa Melody; Minecraft mantém authority dos sliders/categorias de som.
- **Compatibilidade/Riscos:** Biblioteca client-side de áudio. Riscos: leak de OpenAL/listeners, reload do SoundEngine, threading/render-thread, remote audio I/O, collision em mixins de SoundEngine e drift com FancyMenu. Loader metadata usa BOTH, mas reprodução/APIs concretas são client-bound.
- **Observações:** Runtime 1.0.10. Branch oficial 1.21.1 também declara 1.0.10. MixinSoundEngine notifica mudanças de volume e reload; consumers devem fechar clips e unregister listeners. URLs web só são acessadas quando um consumer fornece SourceType WEB.
- **Procedência:** modlist.txt física atual de 10/09/2026 + source oficial Keksuccino/Melody branch 1.21.1 exatamente 1.0.10 + metadata, API de áudio e mixins auditados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/melody
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 1.0.10 exato; AudioClip/OpenAL, OGG/WAV resource/local/web, SoundEngine observers, threading, client boundary, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:** 2026-08-26 — classificado como Dependência após confirmação de FancyMenu instalado e da relação oficial obrigatória FancyMenu → Melody.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> 🎵 **ESCOPO CANÔNICO.** Runtime físico: `melody_neoforge_1.0.10_MC_1.21.jar`, mod id `melody`, versão `1.0.10`, com `melody.mixins.json` e `melody.neoforge.mixins.json`. O source oficial `Keksuccino/Melody`, branch `1.21.1`, declara exatamente `mod_version=1.0.10`, Minecraft 1.21.1 e Java 21; esta é a source-line usada como authority técnica.

## 1. Papel
Melody é uma biblioteca de áudio baseada em **OpenAL** para mods clientes tocarem música/áudio de fundo fora da abstração normal de `SoundEvent`. No pack, o consumer funcional confirmado é **FancyMenu**.
Ela não é jukebox, music disc pack nem sistema de música adaptativa autônomo: fornece primitives de reprodução, buffers/sources OpenAL, carregamento de arquivos/recursos/web e observação das configurações de som do Minecraft para que consumers implementem a experiência final.

## 2. API `AudioClip`
A interface pública `AudioClip` expõe o ciclo de vida essencial:
- `play`, `pause`, `resume`, `stop`;
- `isPlaying`, `isPaused`, `isClosed`;
- `setVolume` / `getVolume`;
- `setSoundChannel` / `getSoundChannel`;
- `close` via `Closeable`.
`play()` reinicia um clip já em reprodução, retoma se pausado e inicia se parado. O volume efetivo é subordinado ao volume MASTER e ao `SoundSource` atribuído, preservando a semântica das categorias de áudio do Minecraft.

## 3. Formatos e fontes de áudio
`SimpleAudioFactory` na branch 1.21.1 fornece criação de clips para **OGG** e **WAV**.
As fontes suportadas incluem:
- `ResourceLocation` do resource manager do Minecraft;
- arquivo local;
- URL web HTTP/HTTPS.
OpenAL source creation exige render thread; decodificação/leitura é deslocada para threads auxiliares e retorna `CompletableFuture<ALAudioClip>`.
Esse desenho significa que consumers precisam lidar com conclusão assíncrona, exceções de I/O/codec e fechamento de resources. A existência de URL web também torna rede externa uma responsabilidade do consumer que fornece a URL, não uma playlist automática do Melody.

## 4. OpenAL e recursos nativos
A implementação possui classes próprias para `ALAudioBuffer`, `ALAudioClip`, error handling e utilitários OpenAL. Buffers/sources são recursos nativos e precisam ser fechados; falhas em lifecycle podem aparecer como leak de áudio, source esgotada ou clip sobrevivendo a telas/reloads.
`SimpleAudioFactory` valida se OpenAL está pronto antes de criar clips e fecha o clip quando a criação/carregamento falha.

## 5. Integração com o SoundEngine do Minecraft
`MixinSoundEngine` injeta em dois pontos client-side:
- após `SoundEngine.updateCategoryVolume`, notifica listeners registrados em `MinecraftSoundSettingsObserver` com o volume efetivo da categoria;
- após `SoundEngine.reload`, executa listeners de reload.
Isso permite que audio clips externos ao pipeline normal acompanhem mudanças do slider de som e reconstruam/reajam quando o motor de som é recarregado.

## 6. `MinecraftSoundSettingsObserver`
A biblioteca mantém dois mapas estáticos de listeners:
- volume listeners, `BiConsumer<SoundSource, Float>`;
- sound-engine reload listeners, `Runnable`.
Cada registro recebe um ID incremental para posterior unregister. `get...Listeners()` retorna cópia dos valores, evitando expor diretamente o mapa interno.
Consumers precisam remover listeners quando deixam de usá-los; a API não demonstra ownership automático de lifecycle de telas/mod consumers.

## 7. Mixins e client boundary
A árvore 1.21.1 contém mixins sobre classes client de som e interfaces accessor para `SoundEngine`/`SoundManager`. O comportamento funcional é client-side e depende de `Minecraft.getInstance()`, render thread e OpenAL.
O template NeoForge declara dependências de NeoForge/Minecraft com `side=BOTH`, mas isso descreve metadata de carregamento; a própria implementação de áudio é client-bound. Não usar a declaração BOTH como prova de que Melody executa reprodução em dedicated server.

## 8. Conteúdo registrado e persistência
Não foi identificado gameplay content próprio — blocos, itens, entidades, recipes, worldgen, atributos ou loot — na source-line auditada. O valor do mod está na biblioteca/API.
Também não foi identificado SavedData/capability de música persistente. Estado de clips/listeners é de processo/cliente. O consumer decide o que deve sobreviver a troca de tela, resource reload ou conexão de servidor.

## 9. Dependências e integração no pack
- Minecraft 1.21.1 / Java 21 na source-line.
- NeoForge de desenvolvimento 21.1.47; runtime físico do pack é 21.1.248.
- **FancyMenu** é consumer confirmado na modlist/projeto e torna Melody uma dependência operacional enquanto usar suas features de áudio.
Não foi encontrada hard dependency de FancyMenu dentro do Melody; ownership é inverso: FancyMenu consome Melody.

## 10. Segurança/privacidade e rede
A API aceita URL HTTP/HTTPS e usa `HttpURLConnection` para abrir recursos web. Isso só ocorre quando um consumer fornece uma fonte WEB. Portanto:
- não afirmar que Melody faz telemetria ou downloads por conta própria;
- URLs remotas em configs de consumers podem revelar IP ao host e depender de disponibilidade/latência externa;
- conteúdo remoto deve ser tratado como entrada externa do consumer.

## 11. Riscos
1. **OpenAL resource leak:** clips/buffers não fechados pelo consumer.
2. **Listener leak:** volume/reload listener não removido.
3. **Sound-engine reload:** consumer não recria estado corretamente após reload.
4. **Threading:** criação de source exige render thread; I/O ocorre async.
5. **Remote audio:** timeout/falha/conteúdo externo pode bloquear ou falhar de forma diferente de ResourceLocation local.
6. **Client-only assumptions:** referência acidental a classes client em contexto server precisa ser evitada pelos consumers.
7. **Mixin collision:** outros mods de áudio podem alterar `SoundEngine.updateCategoryVolume`/`reload`.
8. **Consumer version drift:** FancyMenu pode exigir API behavior de outra versão de Melody.

## 12. Matriz de testes
- [ ] Cliente NeoForge 21.1.248 inicia com Melody 1.0.10 + FancyMenu atual.
- [ ] Dedicated server inicia sem classloading indevido provocado pelo conjunto do pack.
- [ ] OGG por ResourceLocation toca, pausa, retoma, para e fecha corretamente.
- [ ] WAV equivalente funciona quando realmente usado pelo consumer.
- [ ] Volume MASTER e categoria alteram o volume do clip conforme esperado.
- [ ] Resource/sound-engine reload notifica consumer sem duplicar listeners/clips.
- [ ] Abrir/fechar repetidamente a tela que usa música não acumula reprodução nem source OpenAL.
- [ ] Arquivo/URL inválido falha com exceção observável sem deixar clip aberto.
- [ ] Reconnect/server change não deixa música anterior stale quando FancyMenu deveria encerrá-la.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências
- Modlist física: `melody_neoforge_1.0.10_MC_1.21.jar`, runtime 1.0.10, dois mixin configs.
- Source oficial `Keksuccino/Melody`, branch `1.21.1`, head `c8427be511ca8aa29b1c3d747165a66a5fc05fb6`.
- `gradle.properties` exato 1.0.10.
- Arquivos auditados: `AudioClip`, `SimpleAudioFactory`, `MinecraftSoundSettingsObserver`, `MixinSoundEngine`, árvore source e metadata NeoForge.
- Limite: o JAR físico não foi comparado byte-a-byte com o commit; a source-line corresponde por branch/versão/ambiente.