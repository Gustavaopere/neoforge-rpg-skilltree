# Dynamic Brightness

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#233**: JAR `DynamicBrightness-neoforge-1.3.1.jar`, mod id `dynamicbrightness`, runtime `1.3.1`, SHA-1 `c0bddb2585a518a2d5a7183217c607ca3dffc56d`.

## Propriedades do registro

- **Mod:** Dynamic Brightness
- **Arquivo JAR:** `DynamicBrightness-neoforge-1.3.1.jar`
- **Versão 1.21.1:** `1.3.1`
- **Categoria:** Visual, QoL
- **Função:** Efeito client-side de adaptação ocular/exposição que altera dinamicamente a percepção de brilho conforme o ambiente, sem modificar a iluminação real do mundo.
- **Dependências:** NeoForge 1.21.1; função estritamente visual/client-facing. Não possui relação funcional com Dynamic Trees. Deve ser validado junto ao stack gráfico/shaders/dynamic lights do perfil atual.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Conflitos potenciais com auto-exposure, shaders, pós-processamento, fog e outros ajustes de gamma/brightness. Sable Dynamic Lights 2.0.1 está presente, mas controla contribuição de luz dinâmica e não é automaticamente o mesmo domínio. CurseForge e Modrinth classificam o canal da build 1.3.1 de forma diferente; isso é metadata de publicação, não diferença do binário físico.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/dynamic-brightness
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `DynamicBrightness-neoforge-1.3.1.jar` / runtime 1.3.1. CurseForge oficial File ID 5896354 revalidado em 21/09/2026: build NeoForge 1.3.1, publicada como Beta e explicitamente compatível com 1.21.1.
- **Observações:** Runtime 1.3.1 preservado. CurseForge classifica a build NeoForge como Beta e o changelog confirma fix de compatibilidade para Minecraft 1.21/1.21.1. O mod permanece estritamente visual/client-facing e não altera light level real.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 21/09/2026 — lote físico #232: DynamicBrightness-neoforge-1.3.1.jar / runtime 1.3.1 reconfirmados como build NeoForge atual aplicável a Minecraft 1.21.1; CurseForge mantém o arquivo em canal Beta.
- **Decisão:** Sem decisão
- **Sobreposição:** Sobreposição somente visual com auto-exposure/brightness/shader tonemapping. Não substitui dynamic lights nem altera a autoridade de luz/blocos do mundo.

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `DynamicBrightness-neoforge-1.3.1.jar` · mod id `dynamicbrightness` · versão `1.3.1` · NeoForge 1.21.1.
## 1. Papel no modpack
Dynamic Brightness é um mod visual de adaptação de exposição. O objetivo é simular ajuste ocular: a cena percebida fica mais clara ou escura em resposta ao ambiente em vez de manter uma exposição fixa.
## 2. Authority / ownership
O mod controla apenas a transformação/percepção visual no cliente. Minecraft, shaders e providers de iluminação continuam controlando light levels, emissões, céu, fog e render pipelines correspondentes.
**Não** usar o valor visual resultante para inferir light level server-side, spawn eligibility ou iluminação física.
## 3. Build 1.3.1
A build física é 1.3.1 e a publicação oficial registra correção de compatibilidade com Minecraft 1.21/1.21.1. O JAR físico e a versão da modlist coincidem.
Há divergência de classificação entre plataformas: Modrinth apresenta a build como Release, enquanto CurseForge a lista em canal Beta. Isso descreve **metadata de publicação**, não dois binários runtime diferentes nesta ficha.
## 4. Client-side
A função é client-facing. Dedicated server não precisa tratar exposição como gameplay state. Alterar ou desativar o efeito em um cliente não pode mudar o mundo para outros jogadores.
## 5. Relação com luz real
Dynamic Brightness não é um sistema de dynamic lights. Um item emissivo, uma tocha ou Sable Dynamic Lights pode alterar a iluminação que entra no render; Dynamic Brightness altera a resposta visual a essa iluminação.
No pack, Sable Dynamic Lights 2.0.1 está presente. A coexistência deve ser validada visualmente, sem presumir conflito técnico.
## 6. Shaders e pós-processamento
Shaders podem implementar tonemapping, auto-exposure, fog e curvas de luminância próprios. Se dois sistemas corrigirem exposição simultaneamente, o resultado pode oscilar, clarear demais ou destruir contraste.
A precedence correta depende do shader/perfil usado; não há compatibilidade universal presumida.
## 7. Configuração
A configuração deve ser tratada como preferência visual do cliente. Valores exatos/keys não são congelados nesta ficha porque o arquivo de config físico do pack não foi auditado neste ciclo.
## 8. Lifecycle
Validar:
- entrar/sair de cavernas;
- dia/noite;
- Overworld/Nether/End e dimensões com céu customizado;
- resource reload;
- shader reload/toggle;
- respawn;
- mudança de servidor/mundo;
- alteração de gamma/brightness vanilla.
## 9. Multiplayer
Cada cliente pode perceber exposição diferente conforme suas opções gráficas. O servidor continua authority da iluminação e do mundo. Nenhum packet gameplay deve depender da adaptação visual.
## 10. Riscos
1. dupla auto-exposure com shader;
2. flicker ao cruzar áreas claro/escuro;
3. perda de contraste;
4. cavernas artificialmente legíveis demais;
5. interação inesperada com fog/tonemapping;
6. comportamento diferente entre dimensões;
7. resource/shader reload deixando estado visual stale;
8. confundir brilho percebido com light level real;
9. mudança de renderer alterar a curva visual;
10. tratar a classificação Beta/Release da plataforma como diferença de versão instalada.
## 11. Matriz de testes
1. Vanilla renderer sem shader.
2. Shader principal do pack, se habilitado.
3. Caverna escura → superfície ensolarada → caverna.
4. Dia/noite e chuva/tempestade.
5. Nether e End.
6. Sable Dynamic Lights com item/luz móvel.
7. Toggle/reload de shader.
8. Alteração de gamma e GUI/video settings.
9. Relog e troca de mundo.
10. Dedicated server: confirmar ausência de efeito gameplay.
**Esta catalogação não afirma que esses testes foram executados.**
## 12. Evidências
- modlist física atual de 21/09/2026: JAR/mod id/versão 1.3.1;
- publicação oficial 1.3.1 para NeoForge 1.21.1: correção de compatibilidade 1.21/1.21.1;
- metadata de publicação: classificação de canal divergente entre Modrinth e CurseForge.
> **Boundary canônico:** Dynamic Brightness altera **percepção/exposição no cliente**. Não é authority da iluminação do mundo.
